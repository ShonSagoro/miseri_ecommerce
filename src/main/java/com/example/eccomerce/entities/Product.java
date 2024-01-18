package com.example.eccomerce.entities;

import com.example.eccomerce.entities.pivot.OrderProduct;
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
    private Float price;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "product")
    private List<OrderProduct> order_products;

    @OneToMany(mappedBy = "product")
    private List<ProductPromotion> product_promotions;
}
