package com.fawry.shipping_api.controller;

import com.fawry.shipping_api.dto.OrderShipmentDTO;
import com.fawry.shipping_api.service.OrderShipmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderShipmentController {
    private final OrderShipmentService orderShipmentService;

    @PostMapping("/order-shipments")
    public ResponseEntity<OrderShipmentDTO> createOrderShipment(
            @Valid @RequestBody OrderShipmentDTO orderShipmentDTO
    ) {
        OrderShipmentDTO createdOrderShipmentDTO = orderShipmentService.createOrderShipment(orderShipmentDTO);
        return new ResponseEntity<>(createdOrderShipmentDTO, HttpStatus.CREATED);
    }

    @GetMapping("/order-shipments")
    public ResponseEntity<List<OrderShipmentDTO>> getOrderShipments() {
        return new ResponseEntity<>(orderShipmentService.getOrderShipments(), HttpStatus.OK);
    }

    @GetMapping("/order-shipments/{id}")
    public ResponseEntity<OrderShipmentDTO> getOrderShipmentById(@PathVariable Long id) {
        return new ResponseEntity<>(orderShipmentService.getOrderShipmentById(id), HttpStatus.OK);
    }

    @GetMapping("/order-shipments/track/{id}/{trackingToken}")
    public ResponseEntity<OrderShipmentDTO> trackOrderShipment(
            @PathVariable Long id,
            @PathVariable String trackingToken
    ) {
        if( orderShipmentService.validateTrackingToken(id , trackingToken) ){
            return new ResponseEntity<>(orderShipmentService.getOrderShipmentById(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/order-shipments/status")
    public ResponseEntity<OrderShipmentDTO> updateOrderShipmentStatus(
            @RequestBody OrderShipmentDTO orderShipmentDTO
    ) {
        return new ResponseEntity<>(orderShipmentService.updateOrderShipmentStatus(orderShipmentDTO), HttpStatus.OK);
    }

    @PostMapping("/order-shipments/confirm-delivery")
    public ResponseEntity<Boolean> confirmDelivery(
            @RequestBody OrderShipmentDTO orderShipmentDTO
    ){
        return new ResponseEntity<Boolean>(orderShipmentService.confirmOrderShipment(orderShipmentDTO), HttpStatus.OK);
    }

    @PostMapping("/order-shipments/cancel/{id}")
    public ResponseEntity<Boolean> cancelOrderShipment(@PathVariable Long id) {
        return new ResponseEntity<>(orderShipmentService.cancelOrderShipment(id), HttpStatus.OK);
    }
}
