package com.fawry.shipping_api.service.impl;

import com.fawry.shipping_api.dto.shipment.*;
import com.fawry.shipping_api.entity.Customer;
import com.fawry.shipping_api.entity.DeliveryPerson;
import com.fawry.shipping_api.entity.Shipment;
import com.fawry.shipping_api.enums.ResourceType;
import com.fawry.shipping_api.enums.ShippingStatus;
import com.fawry.shipping_api.exception.DuplicateResourceException;
import com.fawry.shipping_api.exception.EntityNotFoundException;
import com.fawry.shipping_api.exception.IllegalActionException;
import com.fawry.shipping_api.mapper.ShipmentMapper;
import com.fawry.shipping_api.repository.ShipmentRepository;
import com.fawry.shipping_api.service.CustomerService;
import com.fawry.shipping_api.service.DeliveryPersonService;
import com.fawry.shipping_api.service.ShipmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ShipmentServiceImpl implements ShipmentService {
    private final ShipmentMapper shipmentMapper;
    private final ShipmentRepository shipmentRepository;
    private final CustomerService customerService;
    private final DeliveryPersonService deliveryPersonService;

    @Override
    public ShipmentDetails getShipmentById(Long id) {
        Shipment shipment = validateShipmentId(id);
        return shipmentMapper.toShipmentDetails(shipment);
    }

    @Override
    public ShipmentDetails processShipment(Long shipmentId) {
        Shipment shipment = validateShipmentId(shipmentId);
        validateStatusTransition(shipment , ShippingStatus.PROCESSED);
        return shipmentMapper.toShipmentDetails(shipment);
    }

    @Override
    public ShipmentDetails shipShipment(Long shipmentId) {
        Shipment shipment = validateShipmentId(shipmentId);
        // TODO: setup delivery person for this shipmentId

        assignDeliveryPerson(shipment);
        validateStatusTransition(shipment , ShippingStatus.SHIPPED);
        return shipmentMapper.toShipmentDetails(shipment);
    }

    @Override
    public ShipmentTracking getShipmentTrackingByToken(Long id, String trackingToken) {
        Shipment shipment = validateShipmentId(id);
        if(!shipment.getTrackingToken().equals(trackingToken)) {
            log.error("Invalid trackingToken");
            throw new IllegalActionException("Invalid trackingToken");
        }

        return shipmentMapper.toShipmentTracking(shipment);
    }


    @Override
    public ShipmentDetails createShipment(CreateShipment createShipment) {
        shipmentRepository.findByOrderId(createShipment.orderId())
                .ifPresent(shipment -> {
                    log.error("Shipment creation failed. A shipment already exists with order ID: {}", createShipment.orderId());
                    throw new DuplicateResourceException(String.format("Shipment creation failed. A shipment already exists with order ID: %s", createShipment.orderId()), ResourceType.SHIPMENT);
                });

        Customer customer = customerService.createCustomer(createShipment.customerDetails());
        Shipment shipment = Shipment.builder()
                .orderId(createShipment.orderId())
                .customer(customer)
                .status(ShippingStatus.RECEIVED)
                .build();

        shipment.setStatus(ShippingStatus.RECEIVED);
        // TODO: Send an email to customer with [trackingLink]

        return shipmentMapper.toShipmentDetails(shipment);
    }

    @Override
    public List<ShipmentDetails> getShipments() {
        List<Shipment> shipmentDetailsList = shipmentRepository.findAll();
        return shipmentDetailsList.stream()
                .map(shipmentMapper::toShipmentDetails)
                .toList();
    }

    @Override
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
    public Boolean confirmShipment(ConfirmShipment confirmShipment) {
        Shipment shipment = validateShipmentId(confirmShipment.shipmentId());

        if(shipment.getStatus() == ShippingStatus.SHIPPED &&
                shipment.getConfirmationCode().equals(confirmShipment.confirmationCode())) {
            shipment.setStatus(ShippingStatus.DELIVERED);
            return true;
        }
        if(!shipment.getConfirmationCode().equals(confirmShipment.confirmationCode())){
            log.error("The confirmation code doesn't match: {}", confirmShipment.confirmationCode());
            throw new IllegalActionException("The confirmation code doesn't match.");
        }
        validateStatusTransition(shipment , ShippingStatus.DELIVERED);
        return true;
    }

    @Override
    public List<ShipmentDeliveryDetails> getDeliveryListByUserId(Long userId) {
        DeliveryPerson person = deliveryPersonService.getDeliveryPerson(userId);
        List<Shipment> shipmentList = shipmentRepository.findByDeliveryPersonPersonId(person.getPersonId());
        return shipmentList.stream()
                .filter(shipment -> shipment.getStatus() == ShippingStatus.SHIPPED)
                .map(shipmentMapper::toShipmentDeliveryDetails)
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

    private void assignDeliveryPerson(Shipment shipment) {
        // TODO: Find the nearest delivery person to this shipment
    }
}