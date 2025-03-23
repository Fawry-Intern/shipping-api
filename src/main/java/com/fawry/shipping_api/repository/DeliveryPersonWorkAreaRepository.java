package com.fawry.shipping_api.repository;

import com.fawry.shipping_api.entity.WorkArea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryPersonWorkAreaRepository extends JpaRepository<WorkArea, Long> {

}
