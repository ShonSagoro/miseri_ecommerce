package com.example.eccomerce.controllers.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductCantRequest {
    private Long id;
    private Integer cant;
}
