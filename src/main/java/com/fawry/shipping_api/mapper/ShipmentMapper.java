package com.fawry.shipping_api.mapper;

import com.fawry.shipping_api.dto.customer.CustomerDetails;
import com.fawry.shipping_api.dto.shipment.ShipmentDeliveryDetails;
import com.fawry.shipping_api.dto.shipment.ShipmentDetails;
import com.fawry.shipping_api.dto.shipment.ShipmentTracking;
import com.fawry.shipping_api.entity.Shipment;
import com.fawry.shipping_api.enums.ShippingStatus;
import org.springframework.stereotype.Component;

@Component
public class ShipmentMapper {
    CustomerMapper customerMapper;
    DeliveryMapper deliveryMapper;
    public ShipmentDetails toShipmentDetails(Shipment shipment) {
        return ShipmentDetails.builder()
                .shipmentId(shipment.getShipmentId())
                .orderId(shipment.getOrderId())
                .customerDetails(customerMapper.toDTO(shipment.getCustomer()))
                .deliveryPerson(deliveryMapper.toDTO(shipment.getDeliveryPerson()))
                .build();
    }

    public ShipmentTracking toShipmentTracking(Shipment shipment) {
        return ShipmentTracking.builder()
                .shipmentId(shipment.getShipmentId())
                .orderId(shipment.getOrderId())
                .deliveryPersonName(shipment.getDeliveryPerson().getName())
                .status(shipment.getStatus())
                .expectedDeliveryDate(shipment.getExpectedDeliveryDate())
                .build();
    }

    public ShipmentDeliveryDetails toShipmentDeliveryDetails(Shipment shipment) {
        return ShipmentDeliveryDetails.builder()
                .shipmentId(shipment.getShipmentId())
                .orderId(shipment.getOrderId())
                .customerDetails(customerMapper.toDTO(shipment.getCustomer()))
                .status(shipment.getStatus())
                .build();
    }
}
