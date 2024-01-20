package com.example.eccomerce.services.interfaces;

import com.example.eccomerce.controllers.dtos.request.CreateOrderRequest;
import com.example.eccomerce.controllers.dtos.response.BaseResponse;
import com.example.eccomerce.controllers.dtos.response.GetOrderResponse;
import com.example.eccomerce.entities.Order;

import java.util.List;

public interface IOrderServices {
    BaseResponse create(CreateOrderRequest request);

    void delete(Long id);

    BaseResponse list();

    BaseResponse get(Long id);

    Order findById(Long id);

    BaseResponse getByDate(String startMonth, String endMonth);

    BaseResponse getByType(String paymentMethod);


    List<GetOrderResponse> findOrderByUserId(Long id);

    GetOrderResponse findResponseById(Long id);


}
