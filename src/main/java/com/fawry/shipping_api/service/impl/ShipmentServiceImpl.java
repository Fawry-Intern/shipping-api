package com.fawry.shipping_api.service.impl;

import com.fawry.shipping_api.dto.shipment.*;
import com.fawry.shipping_api.entity.Customer;
import com.fawry.shipping_api.entity.DeliveryPerson;
import com.fawry.shipping_api.entity.DeliveryAssignment;
import com.fawry.shipping_api.entity.Shipment;
import com.fawry.shipping_api.entity.OrderArea;
import com.fawry.shipping_api.enums.ResourceType;
import com.fawry.shipping_api.enums.ShippingStatus;
import com.fawry.shipping_api.exception.DuplicateResourceException;
import com.fawry.shipping_api.exception.EntityNotFoundException;
import com.fawry.shipping_api.exception.IllegalActionException;
import com.fawry.shipping_api.kafka.events.ShippingStatusEvent;
import com.fawry.shipping_api.kafka.producer.ShippingProducer;
import com.fawry.shipping_api.mapper.CustomerMapper;
import com.fawry.shipping_api.mapper.ShipmentMapper;
import com.fawry.shipping_api.repository.ShipmentRepository;
import com.fawry.shipping_api.service.CustomerService;
import com.fawry.shipping_api.service.DeliveryPersonService;
import com.fawry.shipping_api.service.ShipmentService;
import com.fawry.shipping_api.service.OrderAreaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShipmentServiceImpl implements ShipmentService {
    private final ShipmentMapper shipmentMapper;
    private final CustomerMapper customerMapper;
    private final ShipmentRepository shipmentRepository;
    private final CustomerService customerService;
    private final OrderAreaService orderAreaService;
    private final DeliveryPersonService deliveryPersonService;
    private final ShippingProducer shippingProducer;

    @Value("${frontend.url}")
    private String frontendUrl;

    @Value("${frontend.port}")
    private int frontendPort;



    @Override
    @Transactional
    public ShipmentDetails processShipment(Long shipmentId) {
        Shipment shipment = validateShipmentId(shipmentId);
        validateStatusTransition(shipment , ShippingStatus.PROCESSED);

        ShippingStatusEvent shippingStatusEvent=new ShippingStatusEvent
                (shipment.getCustomer().getEmail(),createNewTrackingLink(shipment.getTrackingToken())
                        ,shipment.getConfirmationCode(),shipment.getOrderId(),ShippingStatus.PROCESSED);
        shippingProducer.sendEvent(shippingStatusEvent, 0);
        return shipmentMapper.toShipmentDetails(shipment);
    }

    @Override
    @Transactional
    public ShipmentDetails shipShipment(Long shipmentId) {
        Shipment shipment = validateShipmentId(shipmentId);

        DeliveryPerson deliveryPerson=assignDeliveryPerson(shipment);
        Optional<OrderArea> workArea=orderAreaService.findByCity(shipment.getCustomer().getCity());

        DeliveryAssignment deliveryAssignment= DeliveryAssignment.builder()
                .deliveryPerson(deliveryPerson)
                .orderArea(workArea.get())
                .build();
        orderAreaService.createDeliveryAssignment(deliveryAssignment);

        validateStatusTransition(shipment , ShippingStatus.SHIPPED);


        ShippingStatusEvent shippingStatusEvent=new ShippingStatusEvent
                (shipment.getCustomer().getEmail(),createNewTrackingLink(shipment.getTrackingToken())
                        ,shipment.getConfirmationCode(),shipment.getOrderId(),ShippingStatus.SHIPPED);
        shippingProducer.sendEvent(shippingStatusEvent, 0);

        return shipmentMapper.toShipmentDetails(shipment);
    }

    @Override
    public ShipmentTracking getShipmentTrackingByToken(String trackingToken) {
        Optional<Shipment> shipment = shipmentRepository.findByTrackingToken(trackingToken);

        if(shipment.isEmpty())
        {
            log.error("Invalid Tracking Token");
            throw new IllegalActionException("Invalid Tracking Token");
        }

        return shipmentMapper.toShipmentTracking(shipment.get());
    }


    @Override
    @Transactional
    public ShipmentDetails createShipment(CreateShipment createShipment) {
        shipmentRepository.findByOrderId(createShipment.orderId())
                .ifPresent(shipment -> {
                    log.error("Shipment creation failed. A shipment already exists with order ID: {}", createShipment.orderId());
                    throw new DuplicateResourceException(String.format("Shipment creation failed. A shipment already exists with order ID: %s", createShipment.orderId()), ResourceType.SHIPMENT);
                });

        Optional<Customer> existingCustomerOpt = customerService.findByEmail(createShipment.customerDetails().email());
        Customer customer;

        if (existingCustomerOpt.isPresent()) {

            customer = existingCustomerOpt.get();
            customer.setName(createShipment.customerDetails().name());
            customer.setPhoneNumber(createShipment.customerDetails().phone());
            customer.setCity(createShipment.customerDetails().city());
            customer.setGovernorate(createShipment.customerDetails().governorate());
            customerService.update(customer);
        } else {

            customer = customerMapper.toEntity(createShipment.customerDetails());
            customer = customerService.create(createShipment.customerDetails());
        }

        Shipment shipment = Shipment.builder()
                .orderId(createShipment.orderId())
                .customer(customer)
                .status(ShippingStatus.RECEIVED)
                .confirmationCode(generateConfirmationCode())
                .trackingToken(generateTrackingToken())
                .build();

        Optional<OrderArea> workAreaOpt = orderAreaService.findByCity(customer.getCity());
        if (workAreaOpt.isEmpty()) {
            OrderArea workAreaEntity = OrderArea.builder()
                    .governorate(customer.getGovernorate())
                    .city(customer.getCity())
                    .build();
            orderAreaService.createWorkArea(workAreaEntity);
        }
        shipmentRepository.save(shipment);


        String trackingLink=createNewTrackingLink(shipment.getTrackingToken());
        ShippingStatusEvent receivingEvent=new ShippingStatusEvent(createShipment.customerDetails().email(),trackingLink,shipment.getConfirmationCode(),createShipment.orderId(),ShippingStatus.RECEIVED);
        shippingProducer.sendEvent(receivingEvent, 0);


        return shipmentMapper.toShipmentDetails(shipment);
    }

    @Override
    public Page<ShipmentDetails> getShipments(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy));
        Page<Shipment> shipmentPage = shipmentRepository.findAll(pageable);
        return shipmentPage.map(shipmentMapper::toShipmentDetails);
    }


    @Override
    @Transactional
    public Boolean cancelShipment(Long id) {
        Shipment shipment = validateShipmentId(id);

        if (shipment.getStatus() == ShippingStatus.CANCELLED) {
            log.info("Order shipment with ID {} is already cancelled.", shipment.getOrderId());
            return true;
        }

        if (shipment.getStatus().ordinal() >= ShippingStatus.SHIPPED.ordinal()){
            log.info("Order shipment with ID {} can't be cancelled because it has entered the shipping phase.", shipment.getOrderId());
            return false;
        }

        log.info("Order shipment with ID {} successfully cancelled.", shipment.getOrderId());
        // TODO: call Order API to cancel this order

        shipment.setStatus(ShippingStatus.CANCELLED);

        return true;
    }

    @Override
    @Transactional
    public Boolean confirmShipment(ConfirmShipment confirmShipment) {
        Shipment shipment = validateShipmentId(confirmShipment.shipmentId());

        if(shipment.getStatus() == ShippingStatus.SHIPPED &&
                shipment.getConfirmationCode().equals(confirmShipment.confirmationCode())) {
            validateStatusTransition(shipment , ShippingStatus.DELIVERED);
            shipment.setStatus(ShippingStatus.DELIVERED);
            shipment.setDeliveredAt(LocalDateTime.now());
            ShippingStatusEvent shippingStatusEvent=new ShippingStatusEvent
                    (shipment.getCustomer().getEmail(),createNewTrackingLink(shipment.getTrackingToken())
                            ,shipment.getConfirmationCode(),shipment.getOrderId(),ShippingStatus.DELIVERED);
            shippingProducer.sendEvent(shippingStatusEvent, 0);
            return true;
        }
        if(!shipment.getConfirmationCode().equals(confirmShipment.confirmationCode())){
            log.error("The confirmation code doesn't match: {}", confirmShipment.confirmationCode());
            throw new IllegalActionException("The confirmation code doesn't match.");
        }

        return false;
    }

    @Override
    public List<ShipmentDetails> getDeliveryListByEmail(String email) {
        DeliveryPerson person = deliveryPersonService.getDeliveryPerson(email);
        List<Shipment> shipmentList = shipmentRepository.findByDeliveryListByUserId(person.getPersonId(),ShippingStatus.SHIPPED);
        return shipmentList.stream()
                .map(shipmentMapper::toShipmentDetails)
                .toList();
    }


    // === Helper functions ===
    private Shipment validateShipmentId(Long id) {
        return shipmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("shipment operation failed. No shipment found with ID: {}", id);
                    return new EntityNotFoundException(String.format("Shipment with order ID: %s - Not Found.", id));
                });
    }
    private void validateStatusTransition(Shipment shipment, ShippingStatus newStatus) {
        if (!canTransitionTo(shipment.getStatus(), newStatus)) {
            log.error("Invalid status transition from {} to {} for shipment ID {}.",
                    shipment.getStatus(), newStatus, shipment.getOrderId());
            throw new IllegalActionException(String.format("Shipment with order ID: %s - Invalid status transition.", shipment.getOrderId()));

        }
        shipment.setStatus(newStatus);
    }

    private boolean canTransitionTo(ShippingStatus currentStatus, ShippingStatus newStatus) {
        return newStatus.ordinal() - currentStatus.ordinal() == 1;
    }

    private String generateTrackingToken() {
        return UUID.randomUUID().toString();
    }

    private String generateConfirmationCode() {
        return String.format("%06d", new Random().nextInt(1000000));
    }



    private DeliveryPerson assignDeliveryPerson(Shipment shipment) {

        LocalDateTime date = LocalDateTime.now();
        DayOfWeek today = date.getDayOfWeek();
        DayOfWeek nearestDay = today;
        int count = 0;
        Random random = new Random();

        do {

            List<DeliveryPerson> deliveryPeople = orderAreaService.getDeliveryPeopleByWorkDay(nearestDay);

            if (!deliveryPeople.isEmpty()) {

                DeliveryPerson assignedPerson = deliveryPeople.get(random.nextInt(deliveryPeople.size()));
                shipment.setDeliveryPerson(assignedPerson);

                LocalDateTime expectedDate=date.plusDays(count);
                shipment.setExpectedDeliveryDate(expectedDate);

                log.info("Assigned delivery person: {}",assignedPerson.getName());
                return assignedPerson;
            }

            nearestDay = nearestDay.plus(1);
            count++;
        } while (nearestDay != today && count < 7);

        throw new EntityNotFoundException("No available delivery person found for shipment ID: " + shipment.getShipmentId());
    }
    private String createNewTrackingLink(String token) {
        return String.format("%s:%d/customer/order-tracking?token=%s", frontendUrl, frontendPort, token);
    }


}