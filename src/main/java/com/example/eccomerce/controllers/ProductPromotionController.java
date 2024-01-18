package com.example.eccomerce.controllers;

import com.example.eccomerce.controllers.dtos.request.CreateProductPromotionRequest;
import com.example.eccomerce.controllers.dtos.response.BaseResponse;
import com.example.eccomerce.services.interfaces.IProductPromotionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("promotionProduct")
@Controller
public class ProductPromotionController {
    @Autowired
    private IProductPromotionServices service;

    @PostMapping
    public ResponseEntity<BaseResponse> create(@RequestBody CreateProductPromotionRequest request) {
        return service.create(request).apply();
    }

    @GetMapping("promotion/{promotionId}/products")
    public ResponseEntity<BaseResponse> listProducts(@PathVariable Long promotionId) {
        return service.getProductsByIdPromotion(promotionId).apply();
    }

    @GetMapping("product/{productId}/promotions")
    public ResponseEntity<BaseResponse> listPromotion(@PathVariable Long productId) {
        return service.getPromotionsByIdProduct(productId).apply();
    }

}
