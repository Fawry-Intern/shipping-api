package com.fawry.shipping_api.service.impl;

import com.fawry.shipping_api.repository.DeliveryPersonWorkAreaRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryPersonWorkAreaServiceImpl {
    private DeliveryPersonWorkAreaRepository deliveryPersonWorkAreaRepository;
}
