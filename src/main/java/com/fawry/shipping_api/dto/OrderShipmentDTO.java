package com.fawry.shipping_api.dto;

import com.fawry.shipping_api.entity.DeliveryPerson;
import com.fawry.shipping_api.enums.ShippingStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record OrderShipmentDTO(
        Long shipmentId,
        Long orderId,
        Long governorateId,
        Long cityId,
        String customerAddress,
        String customerEmail,
        String customerPhone,
        DeliveryPerson deliveryPerson,
        String trackingToken,
        String confirmationCode,
        ShippingStatus status,
        LocalDateTime expectedDeliveryDate,
        LocalDateTime deliveredAt
) {}