package com.fawry.shipping_api.service.impl;

import com.fawry.shipping_api.entity.DeliveryPerson;
import com.fawry.shipping_api.entity.DeliveryAssignment;
import com.fawry.shipping_api.entity.OrderArea;
import com.fawry.shipping_api.mapper.OrderAreaMapper;
import com.fawry.shipping_api.repository.WorkDayRepository;
import com.fawry.shipping_api.repository.DeliveryAssignmentRepository;
import com.fawry.shipping_api.repository.OrderAreaRepository;
import com.fawry.shipping_api.service.OrderAreaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderAreaServiceImpl implements OrderAreaService {
   private final OrderAreaRepository orderAreaRepository;
   private final WorkDayRepository workDayRepository;
   private final DeliveryAssignmentRepository orderAssignmentRepository;
   private OrderAreaMapper orderAreaMapper;

    @Override
    @Transactional
    public Long createWorkArea( OrderArea workArea) {

        return orderAreaRepository.save(workArea).getId();
    }

    @Override
    @Transactional
    public Long createDeliveryAssignment(DeliveryAssignment workAreaDeliveryPerson) {
        return orderAssignmentRepository.save(workAreaDeliveryPerson).getId();
    }

    @Override
    public List<DeliveryPerson> getDeliveryPeopleByWorkDay(DayOfWeek dayOfWeek) {
        return workDayRepository.findByWorkDay(dayOfWeek);
    }

    @Override
    public Optional<OrderArea> findByCity(String city) {
        return orderAreaRepository.findByCity(city) ;
    }


}
