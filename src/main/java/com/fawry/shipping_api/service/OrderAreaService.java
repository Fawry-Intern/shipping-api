package com.fawry.shipping_api.service;

import com.fawry.shipping_api.entity.DeliveryPerson;
import com.fawry.shipping_api.entity.DeliveryAssignment;
import com.fawry.shipping_api.entity.OrderArea;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

public interface OrderAreaService {

    Long createWorkArea( OrderArea workArea);

    Long createDeliveryAssignment( DeliveryAssignment deliveryAssignment);
   List<DeliveryPerson> getDeliveryPeopleByWorkDay(DayOfWeek dayOfWeek);

    Optional<OrderArea> findByCity(String city);


}
