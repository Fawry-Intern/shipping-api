package com.fawry.shipping_api.service;

import com.fawry.shipping_api.dto.shipment.*;

import java.util.List;

public interface ShipmentService {
    ShipmentDetails createShipment(CreateShipment createShipment);

    List<ShipmentDetails> getShipments();

    ShipmentDetails getShipmentById(Long id);

    ShipmentDetails processShipment(Long shipmentId);

    ShipmentDetails shipShipment(Long shipmentId);

    ShipmentTracking getShipmentTrackingByToken(Long id, String trackingToken);
    Boolean cancelShipment(Long id);

    Boolean confirmShipment(ConfirmShipment confirmShipment);

    List<ShipmentDetails> getDeliveryListByUserId(Long userId);
}
