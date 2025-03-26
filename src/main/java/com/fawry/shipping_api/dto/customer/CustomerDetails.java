package com.fawry.shipping_api.dto.customer;

import lombok.Builder;

@Builder
public record CustomerDetails(
        String governorate,
        String city,
        String name,
        String address,
        String email,
        String phone
){
}
