package com.fawry.shipping_api.mapper;

import com.fawry.shipping_api.dto.delivery.DeliveryPersonCreationDetails;
import com.fawry.shipping_api.dto.delivery.DeliveryPersonDetails;
import com.fawry.shipping_api.entity.DeliveryPerson;
import org.springframework.stereotype.Component;

@Component
public class DeliveryMapper {
    public DeliveryPersonDetails toResponse(DeliveryPerson deliveryPerson)
    {
        return DeliveryPersonDetails.builder()
                .personId(deliveryPerson.getPersonId())
                .name(deliveryPerson.getName())
                .email(deliveryPerson.getEmail())
                .phoneNumber(deliveryPerson.getPhoneNumber())
                .address(deliveryPerson.getAddress())
                .build();
    }
    public DeliveryPerson toEntity(DeliveryPersonCreationDetails deliveryPersonCreationDetails)
    {
        return DeliveryPerson.builder()
                .name(deliveryPersonCreationDetails.firstName()+" "+deliveryPersonCreationDetails.lastName())
                .email(deliveryPersonCreationDetails.email())
                .phoneNumber(deliveryPersonCreationDetails.phoneNumber())
                .address(deliveryPersonCreationDetails.address())
                .build();
    }
}
