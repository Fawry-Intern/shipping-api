package com.fawry.shipping_api.mapper;

import com.fawry.shipping_api.dto.OrderShipmentDTO;
import com.fawry.shipping_api.entity.OrderShipment;
import org.springframework.stereotype.Component;

@Component
public class OrderShipmentMapper {
    public OrderShipment toEntity(OrderShipmentDTO orderShipmentDTO) {
        return OrderShipment.builder()
                .shipmentId(orderShipmentDTO.shipmentId())
                .orderId(orderShipmentDTO.orderId())
                .governorateId(orderShipmentDTO.governorateId())
                .cityId(orderShipmentDTO.cityId())
                .customerAddress(orderShipmentDTO.customerAddress())
                .customerEmail(orderShipmentDTO.customerEmail())
                .customerPhone(orderShipmentDTO.customerPhone())
                .deliveryPerson(orderShipmentDTO.deliveryPerson())
                .trackingToken(orderShipmentDTO.trackingToken())
                .confirmationCode(orderShipmentDTO.confirmationCode())
                .status(orderShipmentDTO.status())
                .expectedDeliveryDate(orderShipmentDTO.expectedDeliveryDate())
                .deliveredAt(orderShipmentDTO.deliveredAt())
                .build();
    }

    public OrderShipmentDTO toDTO(OrderShipment orderShipment) {
        return OrderShipmentDTO.builder()
                .shipmentId(orderShipment.getShipmentId())
                .orderId(orderShipment.getOrderId())
                .governorateId(orderShipment.getGovernorateId())
                .cityId(orderShipment.getCityId())
                .customerAddress(orderShipment.getCustomerAddress())
                .customerEmail(orderShipment.getCustomerEmail())
                .customerPhone(orderShipment.getCustomerPhone())
                .deliveryPerson(orderShipment.getDeliveryPerson())
                .trackingToken(orderShipment.getTrackingToken())
                .confirmationCode(orderShipment.getConfirmationCode())
                .status(orderShipment.getStatus())
                .expectedDeliveryDate(orderShipment.getExpectedDeliveryDate())
                .deliveredAt(orderShipment.getDeliveredAt())
                .build();
    }
}
