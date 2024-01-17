package com.example.eccomerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.eccomerce.controllers.dtos.request.CreatePromotionRequest;
import com.example.eccomerce.controllers.dtos.request.UpdatePromotionRequest;
import com.example.eccomerce.controllers.dtos.response.BaseResponse;
import com.example.eccomerce.services.interfaces.IPromotionServices;

@RequestMapping("promotions")
@Controller
public class PromotionController {
    @Autowired
    private IPromotionServices service;

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> get(@PathVariable Long id) {
        return service.get(id).apply();
    }

    @GetMapping
    public ResponseEntity<BaseResponse> getAll() {
        return service.list().apply();
    }

    @PostMapping
    public ResponseEntity<BaseResponse> create(@RequestBody CreatePromotionRequest request) {
        return service.create(request).apply();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> update(@RequestBody UpdatePromotionRequest request,
            @PathVariable Long id) {

        return service.update(id, request).apply();
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @RequestMapping("health")
    public String health() {
        return "Ok";
    }

}
