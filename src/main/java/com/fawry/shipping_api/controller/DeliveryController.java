package com.fawry.shipping_api.controller;

import com.fawry.shipping_api.dto.delivery.DeliveryPersonCreationDetails;
import com.fawry.shipping_api.service.DeliveryPersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/delivery")
public class DeliveryController {

    private final DeliveryPersonService deliveryPersonService;

    @PostMapping("/create-delivery")
    public ResponseEntity<Long> createDelivery(@RequestBody DeliveryPersonCreationDetails deliveryPersonCreationDetails) {

        return new ResponseEntity<>(deliveryPersonService.createDeliveryPerson(deliveryPersonCreationDetails), HttpStatus.CREATED);

    }

}