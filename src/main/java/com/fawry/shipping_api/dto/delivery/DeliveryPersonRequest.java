package com.fawry.shipping_api.dto.delivery;

public record DeliveryPersonRequest(
        String name,
        String email,
        String phoneNumber
){ }
