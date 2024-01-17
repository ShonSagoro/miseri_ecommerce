package com.example.eccomerce.entities;

import com.example.eccomerce.entities.enums.PromotionType;
import com.example.eccomerce.entities.pivot.ProductPromotion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "promotions")
@Getter
@Setter
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private PromotionType type;

    @Column(nullable = false)
    private Float value;

    @OneToMany(mappedBy = "promotion")
    private List<ProductPromotion> product_promotion;
}
