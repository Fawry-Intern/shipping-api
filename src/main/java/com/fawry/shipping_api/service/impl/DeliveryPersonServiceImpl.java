package com.fawry.shipping_api.service.impl;

import com.fawry.shipping_api.entity.DeliveryPerson;
import com.fawry.shipping_api.exception.EntityNotFoundException;
import com.fawry.shipping_api.repository.DeliveryPersonRepository;
import com.fawry.shipping_api.repository.DeliveryPersonWorkAreaRepository;
import com.fawry.shipping_api.service.DeliveryPersonService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class DeliveryPersonServiceImpl implements DeliveryPersonService {
    private DeliveryPersonRepository deliveryPersonRepository;

    @Override
    public DeliveryPerson getDeliveryPerson(Long id) {
        DeliveryPerson person = deliveryPersonRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Delivery person retrieval failed. No delivery person found with ID: {}", id);
                    return new EntityNotFoundException(String.format("Delivery person with ID: %d not found.", id));
                });
        return person;
    }
}
