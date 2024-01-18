package com.example.eccomerce.controllers;


import com.example.eccomerce.controllers.dtos.request.CreateOrderProductRequest;
import com.example.eccomerce.controllers.dtos.response.BaseResponse;
import com.example.eccomerce.services.interfaces.IOrderProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("orderProduct")
@Controller
public class OrderProductController {
    @Autowired
    private IOrderProductServices service;

    @PostMapping
    public ResponseEntity<BaseResponse> create(@RequestBody CreateOrderProductRequest request) {
        return service.create(request).apply();
    }

    @GetMapping("order/{orderId}/products")
    public ResponseEntity<BaseResponse> listProducts(@PathVariable Long orderId) {
        return service.getProductsByIdOrder(orderId).apply();
    }

    @GetMapping("product/{productId}/orders")
    public ResponseEntity<BaseResponse> listOrders(@PathVariable Long productId){
        return service.getOrdersByProductId(productId).apply();
    }
}
