package com.fawry.shipping_api.service.impl;


import com.fawry.shipping_api.dto.customer.CustomerDetails;
import com.fawry.shipping_api.entity.Customer;
import com.fawry.shipping_api.mapper.CustomerMapper;
import com.fawry.shipping_api.repository.CustomerRepository;
import com.fawry.shipping_api.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public Customer create(CustomerDetails customerDetails)
    {
         Customer customer =customerMapper.toEntity(customerDetails);
        return customerRepository.save(customer);
    }

    @Override
    public Customer update(Customer customer) {
        return customerRepository.save(customer);
    }


    @Override
    public Optional<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }
}
