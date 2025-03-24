package com.fawry.shipping_api.repository;

import com.fawry.shipping_api.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    Optional<Shipment> findByOrderId(Long orderId);

    List<Shipment> findByDeliveryPersonPersonId(Long personId);
}
