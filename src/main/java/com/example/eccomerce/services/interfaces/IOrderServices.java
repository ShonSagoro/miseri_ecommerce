package com.example.eccomerce.services.interfaces;

import com.example.eccomerce.controllers.dtos.request.CreateOrderRequest;
import com.example.eccomerce.controllers.dtos.request.UpdateOrderRequest;
import com.example.eccomerce.controllers.dtos.response.BaseResponse;
import com.example.eccomerce.controllers.dtos.response.GetOrderResponse;
import com.example.eccomerce.controllers.dtos.response.GetProductResponse;
import com.example.eccomerce.entities.Order;

import java.util.List;

public interface IOrderServices {
    BaseResponse create(CreateOrderRequest request);

    BaseResponse update(Long id, UpdateOrderRequest request);

    void delete(Long id);

    BaseResponse list();

    BaseResponse get(Long id);

    Order findById(Long id);

    BaseResponse getByRequestDate(String startMonth, String endMonth);

    List<GetOrderResponse> findOrderByUserId(Long id);

    GetOrderResponse findResponseById(Long id);


}
