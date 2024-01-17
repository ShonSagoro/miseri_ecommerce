package com.example.eccomerce.controllers.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePromotionRequest {

    private String name;

    private String description;

    private String type;

    private Float value;
}
