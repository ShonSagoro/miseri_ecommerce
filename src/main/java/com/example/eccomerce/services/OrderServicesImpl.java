package com.example.eccomerce.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.eccomerce.controllers.dtos.request.CreateOrderRequest;
import com.example.eccomerce.controllers.dtos.request.UpdateOrderRequest;
import com.example.eccomerce.controllers.dtos.response.BaseResponse;
import com.example.eccomerce.controllers.dtos.response.GetOrderResponse;
import com.example.eccomerce.entities.Order;
import com.example.eccomerce.entities.enums.converters.PaymentMethodTypeConverter;
import com.example.eccomerce.repositories.IOrderRepository;
import com.example.eccomerce.services.interfaces.IOrderServices;
import com.example.eccomerce.services.interfaces.IUserServices;

@Service
public class OrderServicesImpl implements IOrderServices {
    @Autowired
    private IOrderRepository repository;

    @Autowired
    private IUserServices userService;

    @Autowired
    private PaymentMethodTypeConverter converter;

    @Override
    public BaseResponse create(CreateOrderRequest request) {
        Order order = repository.save(from(request));
        return BaseResponse.builder()
                .data(from(order))
                .message("The order was created")
                .success(true)
                .httpStatus(HttpStatus.CREATED).build();
    }

    @Override
    public BaseResponse update(Long id, UpdateOrderRequest request) {
        Order order = findById(id);
        order = repository.save(update(order, request));
        return BaseResponse.builder()
                .data(from(order))
                .message("The order was updated")
                .success(true)
                .httpStatus(HttpStatus.OK).build();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public BaseResponse list() {
        List<GetOrderResponse> responses = repository
                .findAll()
                .stream()
                .map(this::from).toList();
        return BaseResponse.builder()
                .data(responses)
                .message("find all orders")
                .success(true)
                .httpStatus(HttpStatus.FOUND).build();
    }

    @Override
    public BaseResponse get(Long id) {
        Order order = repository.findById(id)
                .orElseThrow(RuntimeException::new);
        return BaseResponse.builder()
                .data(from(order))
                .message("order for: " + id)
                .success(true)
                .httpStatus(HttpStatus.FOUND).build();
    }

    @Override
    public Order findById(Long id) {
        return repository.findById(id)
                .orElseThrow(RuntimeException::new);
    }

    private GetOrderResponse from(Order order) {
        GetOrderResponse response = new GetOrderResponse();
        response.setId(order.getId());
        response.setCostTotal(order.getCostTotal());
        response.setPaymentMethod(converter.convertToDatabaseColumn(order.getType()));
        response.setUserId(order.getUser().getId());
        // response.setProducts(order.getProducts()); //falta la lista de productos por
        // id de orden
        return response;

    }

    private Order from(CreateOrderRequest request) {
        Order order = new Order();
        order.setCostTotal(request.getCostTotal());
        order.setType(converter.convertToEntityAttribute(request.getPaymentMethod()));
        order.setUser(userService.findById(request.getUserId()));
        order.setRequestTime(LocalDateTime.now());
        return order;
    }

    private Order update(Order order, UpdateOrderRequest update) {
        order.setCostTotal(update.getCostTotal());
        order.setType(converter.convertToEntityAttribute(update.getPaymentMethod()));
        order.setRequestTime(LocalDateTime.now());
        return order;
    }

}
