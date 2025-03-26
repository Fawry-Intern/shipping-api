package com.fawry.shipping_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customers", indexes = {
        @Index(name = "idx_customer_email", columnList = "customer_email")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "governorate", nullable = false)
    private String governorate;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "customer_name", nullable = false)
    private String name;

    @Column(name = "customer_address", nullable = false)
    private String address;

    @Column(name = "customer_email", nullable = false, unique = true)
    private String email;

    @Column(name = "customer_phone", nullable = false)
    private String phoneNumber;
}
