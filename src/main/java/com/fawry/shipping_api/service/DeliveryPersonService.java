package com.fawry.shipping_api.service;


import com.fawry.shipping_api.dto.delivery.DeliveryPersonCreationDetails;
import com.fawry.shipping_api.entity.DeliveryPerson;

public interface DeliveryPersonService {
    DeliveryPerson getDeliveryPerson(Long id);

    Long createDeliveryPerson(DeliveryPersonCreationDetails deliveryPersonCreationDetails);
}
