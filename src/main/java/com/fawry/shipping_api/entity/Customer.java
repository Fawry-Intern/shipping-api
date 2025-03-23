package com.fawry.shipping_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "governorate_id", nullable = false)
    private Long governorateId;

    @Column(name = "city_id", nullable = false)
    private Long cityId;

    @Column(name = "customer_name", nullable = false)
    private String name;

    @Column(name = "customer_address", nullable = false)
    private String address;

    @Column(name = "customer_email", nullable = false)
    private String email;

    @Column(name = "customer_phone", nullable = false)
    private String phoneNumber;
}