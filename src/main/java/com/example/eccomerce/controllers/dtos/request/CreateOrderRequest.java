package com.example.eccomerce.controllers.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderRequest {
    
    private Long costTotal;

    private Long paymentMethodId;

    private Long userId;
}