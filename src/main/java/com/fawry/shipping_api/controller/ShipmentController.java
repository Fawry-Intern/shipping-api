package com.fawry.shipping_api.controller;

import com.fawry.shipping_api.dto.delivery.DeliveryPersonCreationDetails;
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


    @PostMapping
    public ResponseEntity<ShipmentDetails> createShipment(
            @Valid @RequestBody CreateShipment createShipment) {
        return ResponseEntity.ok(shipmentService.createShipment(createShipment));
    }

    // === Admin Endpoints ===

    @GetMapping
    public ResponseEntity<List<ShipmentDetails>> getShipments() {
        return ResponseEntity.ok(shipmentService.getShipments());
    }

    @PutMapping("/process/{shipmentId}")
    public ResponseEntity<ShipmentDetails> processShipment(
            @PathVariable Long shipmentId) {
        return ResponseEntity.ok(shipmentService.processShipment(shipmentId));
    }

    @PutMapping("/ship/{shipmentId}")
    public ResponseEntity<ShipmentDetails> shipShipment(
            @PathVariable Long shipmentId) {
        return ResponseEntity.ok(shipmentService.shipShipment(shipmentId));
    }

    // === Customer Endpoints ===

    @PostMapping("/track")
    public ResponseEntity<ShipmentTracking> trackShipment(
         @Valid  @RequestBody TrackedTokenRequest trackedTokenRequest) {
        return ResponseEntity.ok(shipmentService.getShipmentTrackingByToken
                (trackedTokenRequest.id(), trackedTokenRequest.trackingToken()));
    }

    @PutMapping("/cancel/{shipmentId}")
    public ResponseEntity<Boolean> cancelShipment(
            @PathVariable Long shipmentId) {
        return ResponseEntity.ok(shipmentService.cancelShipment(shipmentId));
    }

    // === Delivery Endpoints ===


    @PutMapping("/confirm-delivery")
    public ResponseEntity<Boolean> confirmDelivery(
           @Valid @RequestBody ConfirmShipment confirmShipment) {
        return ResponseEntity.ok(shipmentService.confirmShipment(confirmShipment));
    }

    @GetMapping("/list-delivery/{userId}")
    public ResponseEntity<List<ShipmentDetails>> getDeliveryListByUserId(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(shipmentService.getDeliveryListByUserId(userId));
    }


}