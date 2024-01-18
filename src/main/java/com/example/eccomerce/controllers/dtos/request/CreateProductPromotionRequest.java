package com.example.eccomerce.controllers.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateProductPromotionRequest {
    private Long productId;
    private Long promotionId;
}
