package com.example.eccomerce.entities.pivot;

import com.example.eccomerce.entities.Product;
import com.example.eccomerce.entities.Promotion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product_promotion")
@Getter
@Setter
public class ProductPromotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Promotion promotion;
}
