package com.fawry.shipping_api.repository;

import com.fawry.shipping_api.entity.DeliveryPerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryPersonRepository extends JpaRepository<DeliveryPerson, Long> {



    boolean existsByEmail(String email);

    DeliveryPerson findByEmail(String email);
}
