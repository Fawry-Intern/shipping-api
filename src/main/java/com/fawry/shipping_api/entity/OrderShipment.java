package com.fawry.shipping_api.entity;

import com.fawry.shipping_api.enums.ShippingStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_shipments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderShipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shipmentId;

    private Long orderId;

    private Long governorateId;

    private Long cityId;

    private String customerAddress;

    @ManyToOne
    @JoinColumn(name = "delivery_person_id")
    private DeliveryPerson deliveryPerson;

    private String trackingToken;

    private String confirmationCode;

    @Enumerated(EnumType.STRING)
    private ShippingStatus status;

    private LocalDateTime expectedDeliveryDate;

    private LocalDateTime deliveredAt;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();
}