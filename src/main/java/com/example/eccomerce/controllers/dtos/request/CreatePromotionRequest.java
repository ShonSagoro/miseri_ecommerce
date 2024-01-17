package com.example.eccomerce.controllers.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreatePromotionRequest {
    
    private String name;

    private String description;

    private String type;

    private Float value;
}
