package com.example.eccomerce.services.interfaces;

import com.example.eccomerce.controllers.dtos.request.CreateOrderProductRequest;
import com.example.eccomerce.controllers.dtos.request.CreateProductPromotionRequest;
import com.example.eccomerce.controllers.dtos.response.BaseResponse;
import com.example.eccomerce.controllers.dtos.response.GetProductResponse;

import java.util.List;

public interface IOrderProductServices {
    BaseResponse create(CreateOrderProductRequest request);
    BaseResponse getProductsByIdOrder(Long orderId);

    List<GetProductResponse> getProductsReponseByIdOrder(Long orderId);
    BaseResponse getOrdersByProductId(Long productId);

    void deleteProductByOrderId(Long orderId);
}
