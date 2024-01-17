package com.example.eccomerce.controllers.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetProductResponse {
    private Long id;
    
    private String name;    

    private Long price;

    private String description;
}
