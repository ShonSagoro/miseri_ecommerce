package com.example.eccomerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.example.eccomerce.controllers.dtos.request.CreateProductRequest;
import com.example.eccomerce.controllers.dtos.request.UpdateProductRequest;
import com.example.eccomerce.controllers.dtos.response.BaseResponse;
import com.example.eccomerce.controllers.dtos.response.GetProductResponse;
import com.example.eccomerce.entities.Product;
import com.example.eccomerce.repositories.IProductRepository;
import com.example.eccomerce.services.interfaces.IProductServices;
import org.springframework.stereotype.Service;

@Service
public class ProductServicesImpl implements IProductServices {

    @Autowired
    private IProductRepository repository;

    @Override
    public BaseResponse create(CreateProductRequest request) {
            Product product = repository.save(from(request));
        return BaseResponse.builder()
                .data(from(product))
                .message("The product was created")
                .success(true)
                .httpStatus(HttpStatus.CREATED).build();
    }

    @Override
    public BaseResponse update(Long id, UpdateProductRequest request) {
        Product product = findById(id);
        product = repository.save(update(product, request));
        return BaseResponse.builder()
                .data(from(product))
                .message("The product was updated")
                .success(true)
                .httpStatus(HttpStatus.OK).build();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public BaseResponse list() {
        List<GetProductResponse> responses = repository
                .findAll()
                .stream()
                .map(this::from).toList();
        return BaseResponse.builder()
                .data(responses)
                .message("find all products")
                .success(true)
                .httpStatus(HttpStatus.FOUND).build();
    }

    @Override
    public BaseResponse get(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(RuntimeException::new);
        return BaseResponse.builder()
                .data(from(product))
                .message("product for: " + id)
                .success(true)
                .httpStatus(HttpStatus.FOUND).build();
    }

    @Override
    public Product findById(Long id) {
        return repository.findById(id)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public GetProductResponse findResponseById(Long id) {
        return from(repository.findById(id)
                .orElseThrow(RuntimeException::new));
    }

    private GetProductResponse from(Product product) {
        GetProductResponse response = new GetProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setPrice(product.getPrice());
        response.setDescription(product.getDescription());
        return response;
    }

    private Product from(CreateProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());
        return product;
    }

    private Product update(Product product, UpdateProductRequest update) {
        product.setName(update.getName());
        product.setPrice(update.getPrice());
        product.setDescription(update.getDescription());
        return product;
    }

}
