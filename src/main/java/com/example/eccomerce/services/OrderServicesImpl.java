package com.example.eccomerce.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.example.eccomerce.controllers.dtos.request.CreateOrderProductRequest;
import com.example.eccomerce.controllers.dtos.request.ProductCantRequest;
import com.example.eccomerce.entities.Product;
import com.example.eccomerce.entities.Promotion;
import com.example.eccomerce.entities.enums.PaymentMethodType;
import com.example.eccomerce.entities.pivot.ProductPromotion;
import com.example.eccomerce.services.interfaces.*;
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

    @Autowired
    @Lazy
    private IPromotionServices promotionServices;

    @Autowired
    @Lazy
    private IProductPromotionServices productPromotionServices;

    @Value("${stripe.secret-key}")
    private String stripeSecretKey;

    @Value("${stripe.publishable-key}")
    private String stripePublishableKey;

    private static final String FORMATTIME = "MMM";
    private static final Locale LOCALE = new Locale("es", "ES");
    private List<Product> products = new ArrayList<>();

    @Override
    public BaseResponse create(CreateOrderRequest request) {
        Order order = repository.save(from(request));
        addProducts(request, order);
        addPromotions(request,order);
        String message = simulateTransaction(order) ?
                "The order was created"
                : "The order was created but the transsaction to: " + request.getPaymentMethod() + " not.";
        return BaseResponse.builder()
                .data(from(order))
                .message(message)
                .success(true)
                .httpStatus(HttpStatus.CREATED).build();

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

    private void addPromotions(CreateOrderRequest request, Order order) {
        List<Long> promotionIds = request.getPromotionsId();
        for (Long id : promotionIds) {
            Promotion promotion = promotionServices.findById(id);
            if (promotion != null) {
                for (ProductPromotion productPromotion : promotion.getProduct_promotion()) {
                    applyPromotion(order, promotion);
                }
            }
        }
    }

    private void applyPromotion(Order order, Promotion promotion) {
        switch (promotion.getType()) {
            case AMOUNT:
                order.setCostTotal(order.getCostTotal() - promotion.getValue());
                break;
            case PERCENTAGE:
                order.setCostTotal(order.getCostTotal() - (order.getCostTotal() * promotion.getValue() / 100));
                break;
        }
    }


    private Boolean simulateTransaction(Order order) {
        Float amount = order.getCostTotal();
        switch (order.getType()) {
            case STRIPE:
                return simulateStripeTransaction(amount);
            case PAYPAL:
                return simulatePaypalTransaction(amount);
            default:
                throw new IllegalArgumentException("Unsupported payment method: " + order.getType());
        }
    }

    private Boolean simulateStripeTransaction(Float amount) {
        try {
            Stripe.apiKey = stripeSecretKey;

            Map<String, Object> params = new HashMap<>();
            params.put("amount", amount.intValue() * 100);
            params.put("currency", "usd");
            PaymentIntent paymentIntent = PaymentIntent.create(params);

            paymentIntent.cancel();

            System.out.println("Stripe transaction simulated successfully");
            return true;
        } catch (Exception e) {
            System.out.println("Failed to simulate Stripe transaction: " + e.getMessage());
            return false;
        }
    }

    private Boolean simulatePaypalTransaction(Float amount) {
        // TODO: Implement this method
        return true;
    }

    private void addProducts(CreateOrderRequest request, Order order) {
        List<ProductCantRequest> list = request.getProducts();
        Float costTotal = 0f;
        for (ProductCantRequest productCantRequest : list) {
            CreateOrderProductRequest createOrderRequest = new CreateOrderProductRequest();
            createOrderRequest.setProductId(productCantRequest.getId());
            createOrderRequest.setOrderId(order.getId());
            orderProductServices.create(createOrderRequest);
            if (productCantRequest.getCant()==0)
                productCantRequest.setCant(1);
            costTotal+=(productServices.findById(productCantRequest.getId()).getPrice())*productCantRequest.getCant();
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
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern(FORMATTIME, LOCALE);
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
