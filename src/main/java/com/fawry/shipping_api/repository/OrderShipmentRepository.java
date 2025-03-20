package com.fawry.shipping_api.repository;

import com.fawry.shipping_api.entity.OrderShipment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OrderShipmentRepository extends JpaRepository<OrderShipment, Long> {
    Optional<OrderShipment> findByOrderId(Long orderId);
}
