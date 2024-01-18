package com.example.eccomerce.controllers.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderProductRequest {
    private Long orderId;
    private Long productId;
}
