package com.fawry.shipping_api.repository;

import com.fawry.shipping_api.dto.customer.CustomerDetails;
import com.fawry.shipping_api.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

   Optional<Customer> findByEmail(String email);
}
