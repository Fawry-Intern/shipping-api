package com.fawry.shipping_api.service.impl;

import com.fawry.shipping_api.dto.delivery.DeliveryPersonCreationDetails;
import com.fawry.shipping_api.entity.DeliveryPerson;
import com.fawry.shipping_api.entity.WorkDay;
import com.fawry.shipping_api.enums.ResourceType;
import com.fawry.shipping_api.exception.DuplicateResourceException;
import com.fawry.shipping_api.exception.EntityNotFoundException;
import com.fawry.shipping_api.mapper.DeliveryMapper;
import com.fawry.shipping_api.repository.DeliveryPersonRepository;
import com.fawry.shipping_api.repository.WorkDayRepository;
import com.fawry.shipping_api.service.DeliveryPersonService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class DeliveryPersonServiceImpl implements DeliveryPersonService {
    private final DeliveryMapper deliveryMapper;
    private final WorkDayRepository workDayRepository;
    private DeliveryPersonRepository deliveryPersonRepository;

    @Override
    public DeliveryPerson getDeliveryPerson(String email) {
        return deliveryPersonRepository.findByEmail(email);
    }




    @Override
    @Transactional
    public Long createDeliveryPerson(DeliveryPersonCreationDetails deliveryPersonCreationDetails) {

            boolean deliveryExists= deliveryPersonRepository.existsByEmail(deliveryPersonCreationDetails.email());

            if(deliveryExists) throw new DuplicateResourceException("Delivery Person with email: "
                           +deliveryPersonCreationDetails.email()+ " already exists"
                           , ResourceType.DELIVERY);

            DeliveryPerson deliveryPerson=deliveryMapper.toEntity(deliveryPersonCreationDetails);

           DeliveryPerson savedDeliveryPerson= deliveryPersonRepository.save(deliveryPerson);

           Set<WorkDay> deliveryWorkDays=new HashSet<>();
            deliveryPersonCreationDetails.workDays().forEach(workDay->{
                WorkDay workDayEntity= WorkDay.builder()
                        .deliveryPerson(savedDeliveryPerson)
                        .workDay(workDay)
                        .build();
                deliveryWorkDays.add(workDayEntity);
            });
            workDayRepository.saveAll(deliveryWorkDays);

        return savedDeliveryPerson.getPersonId();
    }
}
