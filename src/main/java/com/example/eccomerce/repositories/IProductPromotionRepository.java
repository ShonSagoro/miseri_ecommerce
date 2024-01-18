package com.example.eccomerce.repositories;

import com.example.eccomerce.entities.Product;
import com.example.eccomerce.entities.pivot.ProductPromotion;
import com.example.eccomerce.entities.projections.ProductProjection;
import com.example.eccomerce.entities.projections.PromotionProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductPromotionRepository extends JpaRepository<ProductPromotion, Long> {

    @Query(value = "select products.* from product_promotion " +
            "inner join products on product_promotion.product_id = products.id " +
            "inner join promotions on product_promotion.promotion_id = promotions.id " +
            "where product_promotion.promotion_id = :promotionId", nativeQuery = true)
    List<ProductProjection> listAllProductsByIdPromotion(Long promotionId);

    @Query(value = "select promotions.* from product_promotion " +
            "inner join products on product_promotion.product_id = products.id " +
            "inner join promotions on product_promotion.promotion_id = promotions.id " +
            "where product_promotion.product_id = :productId", nativeQuery = true)
    List<PromotionProjection> listAllPromotionsByIdProduct(Long productId);
}
