package com.example.eccomerce.entities;

import com.example.eccomerce.entities.pivot.OrderProduct;
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

    private Float costTotal;

    @Column(name = "payment_method_type", nullable = false)
    private PaymentMethodType type;

    @Column(name = "request_time")
    private LocalDateTime requestTime;

    @OneToMany(mappedBy = "order")
    private List<OrderProduct> products;

    @ManyToOne
    private User user;
}
