package com.example.eccomerce.services.interfaces;

import com.example.eccomerce.controllers.dtos.request.CreateProductRequest;
import com.example.eccomerce.controllers.dtos.request.UpdateProductRequest;
import com.example.eccomerce.controllers.dtos.response.BaseResponse;
import com.example.eccomerce.entities.Product;

public interface IProductServices {
    BaseResponse create(CreateProductRequest request);

    BaseResponse update(Long id, UpdateProductRequest request);

    void delete(Long id);

    BaseResponse list();

    BaseResponse get(Long id);

    Product findById(Long id);
    
}
