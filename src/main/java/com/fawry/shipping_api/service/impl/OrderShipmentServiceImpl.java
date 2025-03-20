package com.fawry.shipping_api.service.impl;

import com.fawry.shipping_api.dto.OrderShipmentDTO;
import com.fawry.shipping_api.entity.DeliveryPersonWorkArea;
import com.fawry.shipping_api.entity.OrderShipment;
import com.fawry.shipping_api.enums.ShippingStatus;
import com.fawry.shipping_api.exception.EntityAlreadyExistsException;
import com.fawry.shipping_api.exception.EntityNotFoundException;
import com.fawry.shipping_api.exception.EntityValidationException;
import com.fawry.shipping_api.mapper.OrderShipmentMapper;
import com.fawry.shipping_api.repository.OrderShipmentRepository;
import com.fawry.shipping_api.service.DeliveryPersonWorkAreaService;
import com.fawry.shipping_api.service.OrderShipmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderShipmentServiceImpl implements OrderShipmentService {
    private final OrderShipmentMapper orderShipmentMapper;
    private final OrderShipmentRepository orderShipmentRepository;
//    private final DeliveryPersonWorkAreaService deliveryPersonWorkAreaService;

    @Override
    public OrderShipmentDTO createOrderShipment(OrderShipmentDTO orderShipmentDTO) {
        orderShipmentRepository.findByOrderId(orderShipmentDTO.orderId())
                .ifPresent(orderShipment -> {
                    log.error("Order shipment creation failed. Order shipment with ID {} already exists.", orderShipmentDTO.orderId());
                    throw new EntityAlreadyExistsException("OrderShipment", orderShipmentDTO.orderId());
                });

        try {
            OrderShipment orderShipment = orderShipmentMapper.toEntity(orderShipmentDTO);
            orderShipment.setStatus(ShippingStatus.RECEIVED);
            orderShipment.setTrackingToken(generateTrackingToken());
            orderShipment.setConfirmationCode(generateConfirmationCode());

            OrderShipment savedOrderShipment = orderShipmentRepository.save(orderShipment);
            log.info("Order shipment successfully created with ID: {}", savedOrderShipment.getShipmentId());

            return orderShipmentMapper.toDTO(savedOrderShipment);
        } catch (Exception e) {
            log.error("Error occurred while creating order shipment with ID {}: {}", orderShipmentDTO.orderId(), e.getMessage(), e);
            throw new RuntimeException("Failed to create OrderShipment", e);
        }
    }

    @Override
    public OrderShipmentDTO updateOrderShipmentStatus(Long id , ShippingStatus newStatus) {
        OrderShipment orderShipment = orderShipmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Order shipment status update failed. No shipment found with ID: {}", id);
                    throw new EntityNotFoundException("OrderShipment", id);
                });


        if (orderShipment.getStatus() == newStatus) {
            log.warn("Order shipment status update skipped. Shipment with ID {} is already in status {}.", orderShipment.getOrderId(), newStatus);
            return orderShipmentMapper.toDTO(orderShipment);
        }

        validateStatusTransition(orderShipment, newStatus);

        orderShipment.setStatus(newStatus);
        if(newStatus == ShippingStatus.SHIPPED) {
            // TODO: Implement the logic to automated assign delivery person and expected delivery date

        }
        
        log.info("Order shipment with ID {} successfully updated to status {}.", orderShipment.getOrderId(), newStatus);

        return orderShipmentMapper.toDTO(orderShipment);
    }

    @Override
    public OrderShipmentDTO getOrderShipmentById(Long id) {
        OrderShipment orderShipment = orderShipmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Order shipment not found. Attempted to access shipment with non-existing id: {}", id);
                    return new EntityNotFoundException("OrderShipment", id);
                });

        return orderShipmentMapper.toDTO(orderShipment);
    }

    @Override
    public List<OrderShipmentDTO> getOrderShipments() {
        List<OrderShipment> orderShipments = orderShipmentRepository.findAll();

        return orderShipments.stream()
                .map(orderShipmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean cancelOrderShipment(Long id) {
        OrderShipment orderShipment = orderShipmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Order shipment cancellation failed. No shipment found with ID: {}", id);
                    return new EntityNotFoundException("OrderShipment", id);
                });

        if (orderShipment.getStatus() == ShippingStatus.CANCELLED) {
            log.info("Order shipment with ID {} is already cancelled.", orderShipment.getOrderId());
            return true;
        }

        if (orderShipment.getStatus() == ShippingStatus.RECEIVED || orderShipment.getStatus() == ShippingStatus.PROCESSED) {
            orderShipment.setStatus(ShippingStatus.CANCELLED);
            log.info("Order shipment with ID {} successfully cancelled.", orderShipment.getOrderId());
            return true;
        }

        log.warn("Order shipment with ID {} cannot be cancelled. Current status: {}", orderShipment.getOrderId(), orderShipment.getStatus());
        return false;
    }

    @Override
    public Boolean confirmOrderShipment(OrderShipmentDTO orderShipmentDTO) {
        OrderShipment orderShipment = orderShipmentRepository.findById(orderShipmentDTO.shipmentId())
                .orElseThrow(() -> {
                    log.error("Order shipment confirmation failed. No shipment found with ID: {}", orderShipmentDTO.shipmentId());
                    throw new EntityNotFoundException("OrderShipment", orderShipmentDTO.shipmentId());
                });

        validateStatusTransition(orderShipment, ShippingStatus.DELIVERED);

        if (orderShipment.getDeliveryPerson() == null) {
            log.error("Order shipment confirmation failed. Shipment with ID {} is not assigned to a delivery person.", orderShipment.getOrderId());
            throw new EntityValidationException("OrderShipment", orderShipment.getOrderId(), "Shipment is not assigned to a delivery person.");
        }

        if (!orderShipment.getConfirmationCode().equals(orderShipmentDTO.confirmationCode())) {
            log.error("Order shipment confirmation failed. Invalid confirmation code for shipment ID {}.", orderShipment.getOrderId());
            throw new EntityValidationException("OrderShipment", orderShipment.getOrderId(), "Invalid confirmation code.");
        }

        orderShipment.setStatus(ShippingStatus.DELIVERED);
        orderShipment.setDeliveredAt(LocalDateTime.now());
        log.info("Order shipment with ID {} successfully confirmed as delivered.", orderShipment.getOrderId());

        return true;
    }

    @Override
    public Boolean validateTrackingToken(Long id, String trackingToken) {
        OrderShipment orderShipment = orderShipmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("validate Tracking Number failed. No shipment found with ID: {}", id);
                    throw new EntityNotFoundException("OrderShipment", id);
                });

        return orderShipment.getTrackingToken().equals(trackingToken);
    }

    // === Helper functions ===

    private void validateStatusTransition(OrderShipment orderShipment, ShippingStatus newStatus) {
        if (!canTransitionTo(orderShipment.getStatus(), newStatus)) {
            log.error("Invalid status transition from {} to {} for shipment ID {}.",
                    orderShipment.getStatus(), newStatus, orderShipment.getOrderId());
            throw new EntityValidationException("OrderShipment", orderShipment.getOrderId(),
                    "Invalid status transition.");
        }
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
}