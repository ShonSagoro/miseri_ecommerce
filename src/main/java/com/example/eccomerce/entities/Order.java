package com.example.eccomerce.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

import com.example.eccomerce.entities.enums.PaymentMethodType;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long costTotal;

    @OneToMany(mappedBy = "order")
    private List<Product> products;

    @Column(name = "payment_method_type", nullable = false)
    private PaymentMethodType type;

    @Column(name = "request_time")
    private LocalDateTime requestTime;


    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
