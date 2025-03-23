package com.fawry.shipping_api.entity;

import com.fawry.shipping_api.enums.DayOfWeek;
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
@Table(name = "work_areas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_area_id")
    private Long workAreaId;

    @ManyToOne
    @JoinColumn(name = "delivery_person_id", nullable = false)
    private DeliveryPerson deliveryPerson;

    @Column(name = "governorate_id", nullable = false)
    private Long governorateId;

    @Column(name = "city_id", nullable = false)
    private Long cityId;

    @ElementCollection(targetClass = DayOfWeek.class)
    @CollectionTable(name = "work_area_days",
            joinColumns = @JoinColumn(name = "work_area_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "work_day", nullable = false)
    private Set<DayOfWeek> workDays;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}