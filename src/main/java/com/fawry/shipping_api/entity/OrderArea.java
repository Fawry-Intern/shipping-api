package com.fawry.shipping_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(
        name = "order_areas",
        indexes = {
                @Index(name = "idx_city", columnList = "city")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_area_id")
    private Long id;

    @Column(name = "governorate", nullable = false)
    private String governorate;

    @Column(name = "city", nullable = false, unique = true)
    private String city;

    @OneToMany(mappedBy = "orderArea", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DeliveryAssignment> deliveryAssignments;


}
