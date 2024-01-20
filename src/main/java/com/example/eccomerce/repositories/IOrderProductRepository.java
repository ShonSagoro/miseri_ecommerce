package com.example.eccomerce.repositories;

import com.example.eccomerce.entities.pivot.OrderProduct;
import com.example.eccomerce.entities.projections.OrderProjection;
import com.example.eccomerce.entities.projections.ProductProjection;
import com.example.eccomerce.entities.projections.PromotionProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IOrderProductRepository extends JpaRepository<OrderProduct, Long>{

    @Query(value = "select products.* from order_product " +
            "inner join products on order_product.product_id = products.id " +
            "inner join orders on order_product.order_id = orders.id " +
            "where order_product.order_id = :orderId", nativeQuery = true)
    List<ProductProjection> listAllProductsByOrderId(Long orderId);

    @Query(value = "select products.* from order_product " +
            "inner join products on order_product.product_id = products.id " +
            "inner join orders on order_product.order_id = orders.id " +
            "where order_product.product_id = :productId", nativeQuery = true)
    List<OrderProjection> listAllOrdersByProductId(Long productId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM order_product WHERE order_id= :orderId", nativeQuery= true)
    void deleteProductByOrderId(Long orderId);
}
