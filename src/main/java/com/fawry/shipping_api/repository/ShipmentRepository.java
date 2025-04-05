package com.fawry.shipping_api.repository;

import com.fawry.shipping_api.dto.shipment.ShipmentTracking;
import com.fawry.shipping_api.entity.Shipment;
import com.fawry.shipping_api.enums.ShippingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    Optional<Shipment> findByOrderId(Long orderId);

    @Query("""
    select s from Shipment s
    where s.deliveryPerson.personId = :personId
    and s.status = :status
    order by s.shipmentId desc
""")
    List<Shipment> findByDeliveryListByUserId(
            @Param("personId") Long personId,
            @Param("status") ShippingStatus status
    );

    Optional<Shipment> findByTrackingToken(String trackingToken);
}
