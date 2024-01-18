package com.example.eccomerce.controllers.dtos.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class GetOrderProductResponse {
    private Long id;
    private GetOrderResponse order;
    private GetProductResponse product;
}
