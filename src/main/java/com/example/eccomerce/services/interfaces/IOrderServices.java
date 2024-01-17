package com.example.eccomerce.services.interfaces;

import com.example.eccomerce.controllers.dtos.request.CreateOrderRequest;
import com.example.eccomerce.controllers.dtos.request.UpdateOrderRequest;
import com.example.eccomerce.controllers.dtos.response.BaseResponse;
import com.example.eccomerce.entities.Order;

public interface IOrderServices {
   BaseResponse create(CreateOrderRequest request);

    BaseResponse update(Long id, UpdateOrderRequest request);

    void delete(Long id);

    BaseResponse list();

    BaseResponse get(Long id);

    Order findById(Long id);
    
}
