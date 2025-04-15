package com.fawry.shipping_api.controller;

import com.fawry.shipping_api.dto.delivery.DeliveryPersonCreationDetails;
import com.fawry.shipping_api.dto.shipment.*;
import com.fawry.shipping_api.service.ShipmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shipments")
public class ShipmentController {
    private final ShipmentService shipmentService;

    // === Admin Endpoints ===

    @GetMapping
    public ResponseEntity<Page<ShipmentDetails>> getShipments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "shipmentId") String sortBy
    ) {
        Page<ShipmentDetails> shipmentDetails=shipmentService.getShipments(page,size,sortBy);
        return ResponseEntity.ok(shipmentDetails);
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

    @GetMapping("/track")
    public ResponseEntity<ShipmentTracking> trackShipment(@RequestParam String trackingToken) {
        return ResponseEntity.ok(shipmentService.getShipmentTrackingByToken(trackingToken));
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

    @GetMapping("/list-delivery")
    public ResponseEntity<List<ShipmentDetails>> getDeliveryListByEmail(
            @RequestParam String email
    ) {
        return ResponseEntity.ok(shipmentService.getDeliveryListByEmail(email));
    }


}