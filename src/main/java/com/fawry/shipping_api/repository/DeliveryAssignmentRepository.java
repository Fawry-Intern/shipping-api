package com.fawry.shipping_api.repository;

import com.fawry.shipping_api.entity.DeliveryAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryAssignmentRepository extends JpaRepository<DeliveryAssignment, Long> {
}
