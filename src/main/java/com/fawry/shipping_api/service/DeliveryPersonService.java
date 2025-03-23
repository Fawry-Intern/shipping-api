package com.fawry.shipping_api.service;

import com.fawry.shipping_api.dto.delivery.DeliveryPersonDetails;
import com.fawry.shipping_api.dto.delivery.DeliveryPersonRequest;
import com.fawry.shipping_api.entity.DeliveryPerson;

public interface DeliveryPersonService {
    DeliveryPerson getDeliveryPerson(Long id);
}
