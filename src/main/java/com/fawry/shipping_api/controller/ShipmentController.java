package com.fawry.shipping_api.controller;

import com.fawry.shipping_api.dto.shipment.*;
import com.fawry.shipping_api.service.ShipmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shipments")
public class ShipmentController {
    private final ShipmentService shipmentService;

    // === Admin Endpoints ===

    @PostMapping
    public ResponseEntity<ShipmentDetails> createShipment(
            @Valid @RequestBody CreateShipment createShipment) {
        return ResponseEntity.ok(shipmentService.createShipment(createShipment));
    }

    @GetMapping
    public ResponseEntity<List<ShipmentDetails>> getShipments() {
        return ResponseEntity.ok(shipmentService.getShipments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShipmentDetails> getShipmentById(@PathVariable Long id) {
        return ResponseEntity.ok(shipmentService.getShipmentById(id));
    }

    @PostMapping("/process/{shipmentId}")
    public ResponseEntity<ShipmentDetails> processShipment(
            @PathVariable Long shipmentId) {
        return ResponseEntity.ok(shipmentService.processShipment(shipmentId));
    }

    @PostMapping("/ship/{shipmentId}")
    public ResponseEntity<ShipmentDetails> shipShipment(
            @PathVariable Long shipmentId) {
        return ResponseEntity.ok(shipmentService.shipShipment(shipmentId));
    }

    // === Customer Endpoints ===

    @GetMapping("/track/{id}/{trackingToken}")
    public ResponseEntity<ShipmentTracking> trackShipment(
            @PathVariable Long id,
            @PathVariable String trackingToken) {
        return ResponseEntity.ok(shipmentService.getShipmentTrackingByToken(id,trackingToken));
    }

    @PostMapping("/cancel/{shipmentId}")
    public ResponseEntity<Boolean> cancelShipment(
            @PathVariable Long shipmentId) {
        return ResponseEntity.ok(shipmentService.cancelShipment(shipmentId));
    }

    // === Delivery Endpoints ===

    @GetMapping("/list-delivery/delivery-email")
    public ResponseEntity<List<ShipmentDetails>> listDelivery(String deliveryEmail) {
        return null;
    }

    @PostMapping("/confirm-delivery")
    public ResponseEntity<Boolean> confirmDelivery(
            @RequestBody ConfirmShipment confirmShipment) {
        return ResponseEntity.ok(shipmentService.confirmShipment(confirmShipment));
    }

    @GetMapping("/list-delivery/{id}")
    public ResponseEntity<List<ShipmentDeliveryDetails>> getDeliveryListByUserId(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(shipmentService.getDeliveryListByUserId(id));
    }


}