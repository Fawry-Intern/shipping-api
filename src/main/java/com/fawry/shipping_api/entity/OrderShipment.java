package com.fawry.shipping_api.entity;

import com.fawry.shipping_api.enums.ShippingStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_shipments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderShipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipment_id")
    private Long shipmentId;

    @Column(name = "order_id", nullable = false, unique = true)
    private Long orderId;

    @Column(name = "governorate_id", nullable = false)
    private Long governorateId;

    @Column(name = "city_id", nullable = false)
    private Long cityId;

    @Column(name = "customer_address", nullable = false)
    private String customerAddress;

    @Column(name = "customer_email", nullable = false)
    private String customerEmail;

    @Column(name = "customer_phone", nullable = false)
    private String customerPhone;

    @ManyToOne
    @JoinColumn(name = "delivery_person_id")
    private DeliveryPerson deliveryPerson;

    @Column(name = "tracking_token")
    private String trackingToken;

    @Column(name = "confirmation_code")
    private String confirmationCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ShippingStatus status;

    @Column(name = "expected_delivery_date")
    private LocalDateTime expectedDeliveryDate;

    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();
}