package com.fawry.shipping_api.mapper;


import com.fawry.shipping_api.dto.workarea.OrderAreaRequest;
import com.fawry.shipping_api.entity.OrderArea;
import lombok.Builder;

@Builder
public class OrderAreaMapper {

     public OrderArea toEntity(OrderAreaRequest workAreaCreationRequest)
     {
         return OrderArea.builder()
                 .governorate(workAreaCreationRequest.governorate())
                 .city(workAreaCreationRequest.city())
                 .build();
     }

}
