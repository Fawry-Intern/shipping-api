package com.fawry.shipping_api.service;

import com.fawry.shipping_api.entity.DeliveryPersonWorkAreaDTO;

import java.util.List;

public interface DeliveryPersonWorkAreaService {

    DeliveryPersonWorkAreaDTO createWorkArea(DeliveryPersonWorkAreaDTO workAreaDTO);

    DeliveryPersonWorkAreaDTO updateWorkArea(Long id, DeliveryPersonWorkAreaDTO workAreaDTO);

    DeliveryPersonWorkAreaDTO getWorkAreaById(Long id);

    List<DeliveryPersonWorkAreaDTO> getWorkAreasByDeliveryPerson(Long deliveryPersonId);

    Boolean deleteWorkArea(Long id);

    Boolean isThereDeliveryPersonAvailableInArea(Long governorateId, Long cityId);

    Boolean isDeliveryPersonAvailableInArea(Long deliveryPersonId, Long governorateId, Long cityId);

    List<DeliveryPersonWorkAreaDTO> getAvailableDeliveryPersonsInArea(Long governorateId, Long cityId);

    List<DeliveryPersonWorkAreaDTO> getWorkAreasByGovernorate(Long governorateId);

    List<DeliveryPersonWorkAreaDTO> getWorkAreasByCity(Long cityId);
}
