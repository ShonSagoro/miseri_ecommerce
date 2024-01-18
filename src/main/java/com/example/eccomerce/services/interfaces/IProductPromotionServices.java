package com.example.eccomerce.services.interfaces;

import com.example.eccomerce.controllers.dtos.request.CreateProductPromotionRequest;
import com.example.eccomerce.controllers.dtos.response.BaseResponse;

public interface IProductPromotionServices {

    BaseResponse create(CreateProductPromotionRequest request);
    BaseResponse getProductsByIdPromotion(Long promotionId);

    BaseResponse getPromotionsByIdProduct(Long productId);
}
