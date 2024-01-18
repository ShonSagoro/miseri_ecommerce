package com.example.eccomerce.entities.pivot;

import com.example.eccomerce.entities.Order;
import com.example.eccomerce.entities.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_product")
@Getter
@Setter
public class OrderProduct {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        private Order order;

        @ManyToOne
        private Product product;
}
