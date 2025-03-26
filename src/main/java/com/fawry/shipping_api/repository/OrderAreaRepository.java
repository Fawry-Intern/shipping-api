package com.fawry.shipping_api.repository;

import com.fawry.shipping_api.entity.OrderArea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderAreaRepository extends JpaRepository<OrderArea, Long> {

    Optional<OrderArea> findByCity(String city);

}
