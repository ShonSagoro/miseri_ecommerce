package com.example.eccomerce.controllers.dtos.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetOrderResponse {

    private Long id;

    private Float costTotal;

    private String paymentMethod;

    private Long userId;

    private String time;
    
    private List<GetProductResponse> products;
}
