package com.example.eccomerce.controllers.dtos.response;

import lombok.Builder;
import lombok.Setter;

@Builder
@Setter
public class GetProductPromotionResponse {
    private Long id;
    private GetPromotionResponse promotion;
    private GetProductResponse product;
}
