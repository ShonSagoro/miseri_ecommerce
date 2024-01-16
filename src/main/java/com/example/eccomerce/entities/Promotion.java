package com.example.eccomerce.entities;

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

    //    Haz un enum para el tipo de promoción
    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Float discount;

    @OneToMany(mappedBy = "promotion")
    private List<ProductPromotion> product_promotion;
}
