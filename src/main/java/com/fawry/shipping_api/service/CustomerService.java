package com.fawry.shipping_api.service;

import com.fawry.shipping_api.dto.customer.CustomerDetails;
import com.fawry.shipping_api.entity.Customer;

public interface CustomerService {
    Customer createCustomer(CustomerDetails customer);
}
