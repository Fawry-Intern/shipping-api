package com.fawry.shipping_api.service;

import com.fawry.shipping_api.dto.OrderShipmentDTO;
import com.fawry.shipping_api.enums.ShippingStatus;

import java.util.List;

public interface OrderShipmentService {
    OrderShipmentDTO createOrderShipment(OrderShipmentDTO orderShipmentDTO);

    OrderShipmentDTO updateOrderShipmentStatus(Long id , ShippingStatus newStatus);

    OrderShipmentDTO getOrderShipmentById(Long id);

    List<OrderShipmentDTO> getOrderShipments();

    Boolean cancelOrderShipment(Long id);

    Boolean confirmOrderShipment(OrderShipmentDTO orderShipmentDTO);

    Boolean validateTrackingToken(Long id, String trackingToken);
}
