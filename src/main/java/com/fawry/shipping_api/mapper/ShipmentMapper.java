package com.fawry.shipping_api.mapper;

import com.fawry.shipping_api.dto.shipment.ShipmentDetails;
import com.fawry.shipping_api.dto.shipment.ShipmentTracking;
import com.fawry.shipping_api.entity.Shipment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ShipmentMapper {
    CustomerMapper customerMapper;
    DeliveryMapper deliveryMapper;
    public ShipmentDetails toShipmentDetails(Shipment shipment) {
        return ShipmentDetails.builder()
                .shipmentId(shipment.getShipmentId())
                .orderId(shipment.getOrderId())
                .customerDetails(customerMapper.toResponse(shipment.getCustomer()))
                .deliveryPerson(shipment.getDeliveryPerson() != null ? deliveryMapper.toResponse(shipment.getDeliveryPerson()) : null)
                .status(shipment.getStatus())
                .deliveredAt(shipment.getDeliveredAt())
                .expectedDeliveryDate(shipment.getExpectedDeliveryDate())
                .build();
    }

    public ShipmentTracking toShipmentTracking(Shipment shipment) {
        return ShipmentTracking.builder()
                .shipmentId(shipment.getShipmentId())
                .orderId(shipment.getOrderId())
                .deliveryPersonName(shipment.getDeliveryPerson()!=null ? shipment.getDeliveryPerson().getName():null)
                .status(shipment.getStatus())
                .expectedDeliveryDate(shipment.getExpectedDeliveryDate())
                .build();
    }


}
