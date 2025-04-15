package com.fawry.shipping_api.mapper;

import com.fawry.shipping_api.dto.customer.CustomerDetails;
import com.fawry.shipping_api.entity.Customer;
import com.fawry.shipping_api.kafka.events.PaymentCreatedEventDTO;
import lombok.Builder;
import org.springframework.stereotype.Component;

@Builder
@Component
public class CustomerMapper {

    public Customer toEntity(CustomerDetails customerDetails)
    {
        return Customer.builder()
                .governorate(customerDetails.governorate())
                .phoneNumber(customerDetails.phone())
                .email(customerDetails.email())
                .city(customerDetails.city())
                .address(customerDetails.address())
                .name(customerDetails.name())
                .build();
    }

    public CustomerDetails toResponse(Customer customer)
    {
        return CustomerDetails.builder()
                .governorate(customer.getGovernorate())
                .city(customer.getCity())
                .phone(customer.getPhoneNumber())
                .address(customer.getAddress())
                .email(customer.getEmail())
                .name(customer.getName())
                .build();
    }


}
