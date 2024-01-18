package com.example.eccomerce.services;

import com.example.eccomerce.controllers.dtos.response.BaseResponse;
import com.example.eccomerce.controllers.dtos.response.GetProductResponse;
import com.example.eccomerce.controllers.dtos.response.GetPromotionResponse;
import com.example.eccomerce.entities.pivot.ProductPromotion;
import com.example.eccomerce.entities.projections.ProductProjection;
import com.example.eccomerce.entities.projections.PromotionProjection;
import com.example.eccomerce.repositories.IProductPromotionRepository;
import com.example.eccomerce.services.interfaces.IProductPromotionServices;
import com.example.eccomerce.services.interfaces.IProductServices;
import com.example.eccomerce.services.interfaces.IPromotionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoPromotionsServicesImpl implements IProductPromotionServices {

    @Autowired
    private IProductPromotionRepository repository;
    @Autowired
    private IPromotionServices promotionServices;

    @Autowired
    private IProductServices productServices;

    @Override
    public BaseResponse getProductsByIdPromotion(Long promotionId) {
        List<ProductProjection> products= repository.listAllProductsByIdPromotion(promotionId);
        List<GetProductResponse> response=products.stream()
                .map(this::from)
                .toList();
        return BaseResponse.builder()
                .data(response)
                .message("find all products by promotion")
                .success(true)
                .build();
    }

    @Override
    public BaseResponse getPromotionsByIdProduct(Long productId) {
        List<PromotionProjection> promotions= repository.listAllPromotionsByIdProduct(productId);
        List<GetPromotionResponse> response=promotions.stream()
                .map(this::from)
                .toList();
        return BaseResponse.builder()
                .data(response)
                .message("find all promotions by product")
                .success(true)
                .build();
    }

    private GetPromotionResponse from(PromotionProjection promotion) {
        return promotionServices.findResponseById(promotion.getId());
    }

    private GetProductResponse from(ProductProjection product) {
        return productServices.findResponseById(product.getId());
    }


}
