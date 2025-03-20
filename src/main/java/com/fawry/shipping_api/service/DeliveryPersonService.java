package com.fawry.shipping_api.service;

import com.fawry.shipping_api.dto.DeliveryPersonDTO;

import java.util.List;

public interface DeliveryPersonService {

    DeliveryPersonDTO createDeliveryPerson(DeliveryPersonDTO deliveryPersonDTO);

    DeliveryPersonDTO getDeliveryPersonById(Long id);

    List<DeliveryPersonDTO> getAllDeliveryPersons();

    Boolean deleteDeliveryPerson(Long id);

    Boolean isAvailable(Long deliveryPersonId);

    List<DeliveryPersonDTO> getActiveDeliveryPersons();
}