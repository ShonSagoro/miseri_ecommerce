package com.example.eccomerce.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.example.eccomerce.controllers.dtos.request.CreateOrderProductRequest;
import com.example.eccomerce.entities.enums.PaymentMethodType;
import com.example.eccomerce.services.interfaces.IOrderProductServices;
import com.example.eccomerce.services.interfaces.IProductServices;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.eccomerce.controllers.dtos.request.CreateOrderRequest;
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

    @Autowired
    @Lazy
    private IProductServices productServices;

    @Value("${stripe.secret-key}")
    private String stripeSecretKey;

    @Value("${stripe.publishable-key}")
    private String stripePublishableKey;

    private static final String FormatTime = "yyyy-MM-dd";

    @Override
    public BaseResponse create(CreateOrderRequest request) {
        Order order = repository.save(from(request));
        addProducts(request, order);
        return BaseResponse.builder().data(from(order)).message("The order was created").success(true).httpStatus(HttpStatus.CREATED).build();
    }

    @Override
    public void delete(Long id) {
        orderProductServices.deleteProductByOrderId(id);
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
    public BaseResponse getByDate(String startMonth, String endMonth) {
        List<GetOrderResponse> allOrders = repository.findAll().stream().map(this::from).toList();
        List<GetOrderResponse> filteredOrders = allOrders.stream()
                .filter(order -> order.getTime().compareTo(startMonth) >=0 && order.getTime().compareTo(endMonth)<=0)
                .toList();
        return BaseResponse.builder()
                .data(filteredOrders)
                .message("find all orders by date")
                .success(true)
                .httpStatus(HttpStatus.FOUND).build();
    }

    @Override
    public BaseResponse getByType(String paymentMethod) {
        PaymentMethodType type = converter.convertToEntityAttribute(paymentMethod);
        List<GetOrderResponse> allOrders = repository.findByType(type).stream().map(this::from).toList();
        return BaseResponse.builder()
                .data(allOrders)
                .message("find all orders by type")
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
        Float costTotal = 0f;
        for (Long id : list) {
            CreateOrderProductRequest createOrderRequest = new CreateOrderProductRequest();
            createOrderRequest.setProductId(id);
            createOrderRequest.setOrderId(order.getId());
            orderProductServices.create(createOrderRequest);
            costTotal+=productServices.findById(id).getPrice();
        }
        order.setCostTotal(costTotal);
        repository.save(order);
    }

    private GetOrderResponse from(Order order) {
        GetOrderResponse response = new GetOrderResponse();
        response.setId(order.getId());
        response.setCostTotal(order.getCostTotal());
        response.setPaymentMethod(converter.convertToDatabaseColumn(order.getType()));
        response.setUserId(order.getUser().getId());
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMM", new Locale("es", "ES"));
        response.setTime(order.getDate().format(monthFormatter));
        response.setProducts(orderProductServices.getProductsReponseByIdOrder(order.getId()));
        return response;

    }

    private Order from(CreateOrderRequest request) {
        Order order = new Order();
        order.setType(converter.convertToEntityAttribute(request.getPaymentMethod()));
        order.setUser(userService.findById(request.getUserId()));
        order.setDate(LocalDate.now());
        return order;
    }

    private String createPaymentIntent(int amount, String currency) {
        Stripe.apiKey = stripeSecretKey;

        Map<String, Object> params = new HashMap<>();
        params.put("amount", amount);
        params.put("currency", currency);

        try {
            PaymentIntent paymentIntent = PaymentIntent.create(params);
            return paymentIntent.getClientSecret();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private DateTimeFormatter getFormatter(String format) {
        return DateTimeFormatter.ofPattern(format);
    }

}
