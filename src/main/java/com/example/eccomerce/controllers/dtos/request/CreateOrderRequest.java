package com.example.eccomerce.controllers.dtos.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateOrderRequest {

    private String paymentMethod;

    private Long userId;

    private List<ProductCantRequest> products;

    private List<Long> promotionsId;
}
