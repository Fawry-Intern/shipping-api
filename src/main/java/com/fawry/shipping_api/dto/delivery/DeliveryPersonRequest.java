package com.fawry.shipping_api.dto.delivery;

import lombok.Builder;

@Builder
public record DeliveryPersonRequest(
        String name,
        String email,
        String phoneNumber
){ }
