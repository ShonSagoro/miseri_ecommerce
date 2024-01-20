package com.example.eccomerce.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.example.eccomerce.controllers.dtos.request.CreateOrderProductRequest;
import com.example.eccomerce.controllers.dtos.response.GetProductResponse;
import com.example.eccomerce.services.interfaces.IOrderProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
    @Lazy
    private IOrderRepository repository;

    @Autowired
    private IUserServices userService;

    @Autowired
    private PaymentMethodTypeConverter converter;

    @Autowired
    @Lazy
    private IOrderProductServices orderProductServices;

    private static final String FormatTime = "yyyy-MM-dd";

    @Override
    public BaseResponse create(CreateOrderRequest request) {
        Order order = repository.save(from(request));
        addProducts(request, order);
        return BaseResponse.builder().data(from(order)).message("The order was created").success(true).httpStatus(HttpStatus.CREATED).build();
    }

    @Override
    public BaseResponse update(Long id, UpdateOrderRequest request) {
        Order order = findById(id);
        order = repository.save(update(order, request));
        return BaseResponse.builder().data(from(order)).message("The order was updated").success(true).httpStatus(HttpStatus.OK).build();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public BaseResponse list() {
        List<GetOrderResponse> responses = repository.findAll().stream().map(this::from).toList();
        return BaseResponse.builder().data(responses).message("find all orders").success(true).httpStatus(HttpStatus.FOUND).build();
    }

    @Override
    public BaseResponse get(Long id) {
        Order order = repository.findById(id).orElseThrow(RuntimeException::new);
        return BaseResponse.builder().data(from(order)).message("order for: " + id).success(true).httpStatus(HttpStatus.FOUND).build();
    }

    @Override
    public Order findById(Long id) {
        return repository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public BaseResponse getByRequestDate(String startMonth, String endMonth) {
        LocalDate startDate = LocalDate.parse(startMonth + "-01");
        LocalDate endDate = LocalDate.parse(endMonth + "-01").plusMonths(1).minusDays(1);
        List<GetOrderResponse> responses = repository.findByDateBetween(startDate, endDate).stream().map(this::from).toList();
        return BaseResponse.builder()
                .data(responses)
                .message("find all orders by date")
                .success(true)
                .httpStatus(HttpStatus.FOUND).build();
    }

    @Override
    public List<GetOrderResponse> findOrderByUserId(Long id) {
        return repository.findByUserId(id).stream().map(this::from).toList();
    }

    @Override
    public GetOrderResponse findResponseById(Long id) {
        return from(findById(id));
    }

    private void addProducts(CreateOrderRequest request, Order order) {
        List<Long> list = request.getProductsId();
        for (Long id : list) {
            CreateOrderProductRequest createOrderRequest = new CreateOrderProductRequest();
            createOrderRequest.setProductId(id);
            createOrderRequest.setOrderId(order.getId());
            orderProductServices.create(createOrderRequest);
        }
    }

    private GetOrderResponse from(Order order) {
        GetOrderResponse response = new GetOrderResponse();
        response.setId(order.getId());
        response.setCostTotal(order.getCostTotal());
        response.setPaymentMethod(converter.convertToDatabaseColumn(order.getType()));
        response.setUserId(order.getUser().getId());
        response.setTime(order.getDate().format(getFormatter(FormatTime)));
        response.setProducts(orderProductServices.getProductsReponseByIdOrder(order.getId()));
        return response;

    }

    private Order from(CreateOrderRequest request) {
        Order order = new Order();
        order.setCostTotal(request.getCostTotal());
        order.setType(converter.convertToEntityAttribute(request.getPaymentMethod()));
        order.setUser(userService.findById(request.getUserId()));
        order.setDate(LocalDate.now());
        return order;
    }

    private Order update(Order order, UpdateOrderRequest update) {
        order.setCostTotal(update.getCostTotal());
        order.setType(converter.convertToEntityAttribute(update.getPaymentMethod()));
        order.setDate(LocalDate.now());
        return order;
    }

    private DateTimeFormatter getFormatter(String format) {
        return DateTimeFormatter.ofPattern(format);
    }

}
