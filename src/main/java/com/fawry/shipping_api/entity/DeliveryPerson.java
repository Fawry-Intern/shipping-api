package com.fawry.shipping_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "delivery_persons")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_person_id")
    private Long personId;

    @Column(name = "delivery_name")
    private String name;

    @Column(name = "delivery_email", unique = true)
    private String email;

    @Column(name = "delivery_phone")
    private String phoneNumber;

    @OneToMany(mappedBy = "deliveryPerson", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DeliveryAssignment> workAreas;

    @OneToMany(mappedBy = "deliveryPerson", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<WorkDay> workDays;  // Relationship managed from DeliveryPerson side

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
