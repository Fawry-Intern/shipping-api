package com.fawry.shipping_api.service;

import com.fawry.shipping_api.dto.customer.CustomerDetails;
import com.fawry.shipping_api.entity.Customer;

import java.util.Optional;

public interface CustomerService {
    Customer create(CustomerDetails customer);
    Customer update(Customer customer);
   Optional<Customer> findByEmail(String email);

}
