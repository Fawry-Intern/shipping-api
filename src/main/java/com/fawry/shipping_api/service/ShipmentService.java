package com.fawry.shipping_api.service;

import com.fawry.shipping_api.dto.shipment.*;
import com.fawry.shipping_api.kafka.events.PaymentCreatedEventDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ShipmentService {
    void shipOrder(PaymentCreatedEventDTO shippedDetails);

    ShipmentDetails createShipment(CreateShipment createShipment);

    Page<ShipmentDetails> getShipments(int page, int size, String sortBy);


    ShipmentDetails processShipment(Long shipmentId);

    ShipmentDetails shipShipment(Long shipmentId);

    ShipmentTracking getShipmentTrackingByToken(String trackingToken);
    Boolean cancelShipment(Long id);

    Boolean confirmShipment(ConfirmShipment confirmShipment);

    List<ShipmentDetails> getDeliveryListByEmail(String email);
}
