package com.example.eccomerce.controllers.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetPromotionResponse {
    
    private Long id;

    private String name;

    private String description;

    private String type;
    
    private Float value;
}
