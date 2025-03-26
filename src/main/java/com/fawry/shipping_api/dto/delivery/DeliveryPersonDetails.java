package com.fawry.shipping_api.dto.delivery;

import lombok.Builder;

@Builder
public record DeliveryPersonDetails(
        Long personId,
        String name,
        String email,
        String phoneNumber
){ }
