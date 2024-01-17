package com.example.eccomerce.repositories;

import com.example.eccomerce.entities.pivot.ProductPromotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductPromotionRepository extends JpaRepository<ProductPromotion, Long> {
}
