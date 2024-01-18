package com.example.eccomerce.controllers.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class UpdateProductRequest  {
    
    private String name;

    private Float price;

    private String description;

}
