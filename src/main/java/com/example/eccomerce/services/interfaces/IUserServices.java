package com.example.eccomerce.services.interfaces;

import com.example.eccomerce.controllers.dtos.request.CreateUserRequest;
import com.example.eccomerce.controllers.dtos.request.UpdateUserRequest;
import com.example.eccomerce.controllers.dtos.response.BaseResponse;
import com.example.eccomerce.entities.User;

public interface IUserServices {
    BaseResponse create(CreateUserRequest request);

    BaseResponse update(Long id, UpdateUserRequest request);

    void delete(Long id);

    BaseResponse list();

    BaseResponse get(Long id);

    User findById(Long id);

    User findByEmail(String email);

    BaseResponse findOrdersByUserId(Long id);
}