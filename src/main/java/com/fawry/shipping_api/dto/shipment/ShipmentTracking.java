package com.fawry.shipping_api.dto.shipment;

import com.fawry.shipping_api.enums.ShippingStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ShipmentTracking(
    Long shipmentId,
    Long orderId,
    String deliveryPersonName,
    ShippingStatus status,
    LocalDateTime expectedDeliveryDate
) {}