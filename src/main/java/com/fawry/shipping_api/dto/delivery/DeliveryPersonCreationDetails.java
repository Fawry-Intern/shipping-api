package com.fawry.shipping_api.dto.delivery;

import lombok.Builder;

import java.time.DayOfWeek;
import java.util.Set;

@Builder
public record DeliveryPersonCreationDetails(
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String address,
        String password,
        Set<DayOfWeek> workDays
){ }
