package com.fawry.shipping_api.dto.customer;

public record CustomerDetails(
        Long governorateId,
        Long cityId,
        String customerName,
        String customerAddress,
        String customerEmail,
        String customerPhone
){
}
