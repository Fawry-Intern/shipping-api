package com.fawry.shipping_api.service.impl;

import com.fawry.shipping_api.dto.customer.CustomerDetails;
import com.fawry.shipping_api.entity.Customer;
import com.fawry.shipping_api.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class CustomerServiceImpl implements CustomerService {
    @Override
    public Customer createCustomer(CustomerDetails customer) {
        return null;
    }
}
