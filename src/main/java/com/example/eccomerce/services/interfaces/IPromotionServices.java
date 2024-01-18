package com.example.eccomerce.services.interfaces;

import com.example.eccomerce.controllers.dtos.request.CreatePromotionRequest;
import com.example.eccomerce.controllers.dtos.request.UpdatePromotionRequest;
import com.example.eccomerce.controllers.dtos.response.BaseResponse;
import com.example.eccomerce.controllers.dtos.response.GetProductResponse;
import com.example.eccomerce.controllers.dtos.response.GetPromotionResponse;
import com.example.eccomerce.entities.Promotion;

public interface IPromotionServices {
    BaseResponse create(CreatePromotionRequest request);

    BaseResponse update(Long id, UpdatePromotionRequest request);

    void delete(Long id);

    BaseResponse list();

    BaseResponse get(Long id);

    Promotion findById(Long id);

    GetPromotionResponse findResponseById(Long id);

}
