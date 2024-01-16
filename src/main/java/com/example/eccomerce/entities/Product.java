package com.example.eccomerce.entities;

import com.example.eccomerce.entities.pivot.ProductPromotion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long cost;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    private Order order;

    @OneToMany(mappedBy = "product")
    private List<ProductPromotion> product_promotions;
}
