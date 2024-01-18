package com.example.eccomerce.services.interfaces;

import com.example.eccomerce.controllers.dtos.response.BaseResponse;

public interface IProductPromotionServices {
    BaseResponse getProductsByIdPromotion(Long promotionId);

    BaseResponse getPromotionsByIdProduct(Long productId);
}
