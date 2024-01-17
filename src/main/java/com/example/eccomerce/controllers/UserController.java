package com.example.eccomerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.eccomerce.controllers.dtos.request.CreateUserRequest;
import com.example.eccomerce.controllers.dtos.request.UpdateUserRequest;
import com.example.eccomerce.controllers.dtos.response.BaseResponse;
import com.example.eccomerce.services.interfaces.IUserServices;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private IUserServices service;

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> get(@PathVariable Long id) {
        return service.get(id).apply();
    }

    @GetMapping
    public ResponseEntity<BaseResponse> getAll() {
        return service.list().apply();
    }

    @PostMapping("/register")
    public ResponseEntity<BaseResponse> create(@RequestBody CreateUserRequest request) {
        return service.create(request).apply();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> update(@RequestBody UpdateUserRequest request,
            @PathVariable Long id) {

        return service.update(id, request).apply();
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("health")
    public String health() {
        return "Ok";
    }

}
