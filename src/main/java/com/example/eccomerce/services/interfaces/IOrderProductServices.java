package com.example.eccomerce.services.interfaces;

import com.example.eccomerce.controllers.dtos.request.CreateOrderProductRequest;
import com.example.eccomerce.controllers.dtos.request.CreateProductPromotionRequest;
import com.example.eccomerce.controllers.dtos.response.BaseResponse;

public interface IOrderProductServices {
    BaseResponse create(CreateOrderProductRequest request);
    BaseResponse getProductsByIdOrder(Long orderId);

    BaseResponse getOrdersByProductId(Long productId);
}
