package com.fawry.shipping_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "delivery_assignments",
        indexes = {
                @Index(name = "idx_order_area", columnList = "order_area_id"),
                @Index(name = "idx_delivery_person", columnList = "delivery_person_id")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_area_id", nullable = false)
    private OrderArea orderArea;

    @ManyToOne
    @JoinColumn(name = "delivery_person_id", nullable = false)
    private DeliveryPerson deliveryPerson;
}
