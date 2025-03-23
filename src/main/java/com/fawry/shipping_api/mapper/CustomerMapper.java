package com.fawry.shipping_api.mapper;

import com.fawry.shipping_api.dto.customer.CustomerDetails;
import com.fawry.shipping_api.entity.Customer;
import lombok.Builder;
import org.springframework.stereotype.Component;

@Builder
@Component
public class CustomerMapper {

    public Customer toEntity(CustomerDetails customerDetails) {
        return null;
    }

    public CustomerDetails toDTO(Customer customer) {
        return null;
    }
}
