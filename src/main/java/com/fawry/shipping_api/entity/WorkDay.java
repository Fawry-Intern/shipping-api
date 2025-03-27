package com.fawry.shipping_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;

@Entity
@Table(name = "work_days")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "delivery_person_id", nullable = false)
    private DeliveryPerson deliveryPerson;

    @Column(name = "work_day", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private DayOfWeek workDay;
}
