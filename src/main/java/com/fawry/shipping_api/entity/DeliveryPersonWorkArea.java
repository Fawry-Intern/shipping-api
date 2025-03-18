package com.fawry.shipping_api.entity;

import com.fawry.shipping_api.enums.DayOfWeek;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "delivery_person_work_areas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPersonWorkArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workAreaId;

    @ManyToOne
    @JoinColumn(name = "delivery_person_id", nullable = false)
    private DeliveryPerson deliveryPerson;

    @Column(nullable = false)
    private Long governorateId;

    @Column(nullable = false)
    private Long cityId;

    @ElementCollection(targetClass = DayOfWeek.class)
    @CollectionTable(name = "work_area_days",
            joinColumns = @JoinColumn(name = "work_area_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "work_day", nullable = false)
    private Set<DayOfWeek> workDays;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
