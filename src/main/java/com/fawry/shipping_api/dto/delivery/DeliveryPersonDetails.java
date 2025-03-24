package com.fawry.shipping_api.dto.delivery;

public record DeliveryPersonDetails(
        Long personId,
        String name,
        String email,
        String phoneNumber,
        Boolean isActive
){ }
