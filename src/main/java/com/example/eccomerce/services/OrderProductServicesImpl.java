package com.example.eccomerce.services;

import com.example.eccomerce.controllers.dtos.request.CreateOrderProductRequest;
import com.example.eccomerce.controllers.dtos.request.CreateProductPromotionRequest;
import com.example.eccomerce.controllers.dtos.response.*;
import com.example.eccomerce.entities.pivot.OrderProduct;
import com.example.eccomerce.entities.pivot.ProductPromotion;
import com.example.eccomerce.entities.projections.OrderProjection;
import com.example.eccomerce.entities.projections.ProductProjection;
import com.example.eccomerce.entities.projections.PromotionProjection;
import com.example.eccomerce.repositories.IOrderProductRepository;
import com.example.eccomerce.services.interfaces.IOrderProductServices;
import com.example.eccomerce.services.interfaces.IOrderServices;
import com.example.eccomerce.services.interfaces.IProductServices;
import com.example.eccomerce.services.interfaces.IPromotionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderProductServicesImpl implements IOrderProductServices {
    @Autowired
    private IOrderProductRepository repository;
    @Autowired
    @Lazy
    private IOrderServices orderServices;

    @Autowired
    @Lazy
    private IProductServices productServices;

    @Override
    public BaseResponse create(CreateOrderProductRequest request) {
        OrderProduct orderProduct = from(request);
        repository.save(orderProduct);
        return BaseResponse.builder()
                .data(from(orderProduct))
                .message("The order product was created")
                .success(true)
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    @Override
    public BaseResponse getProductsByIdOrder(Long orderId) {
        List<ProductProjection> products = repository.listAllProductsByOrderId(orderId);
        List<GetProductResponse> response = products.stream()
                .map(this::from)
                .toList();
        return BaseResponse.builder()
                .data(response)
                .message("find all products by order")
                .success(true)
                .httpStatus(HttpStatus.OK).build();
    }

    @Override
    public List<GetProductResponse> getProductsReponseByIdOrder(Long orderId) {
        List<ProductProjection> products = repository.listAllProductsByOrderId(orderId);
        return products.stream()
                .map(this::from)
                .toList();
    }

    @Override
    public BaseResponse getOrdersByProductId(Long productId) {
        List<OrderProjection> orders = repository.listAllOrdersByProductId(productId);
        List<GetOrderResponse> response = orders.stream()
                .map(this::from)
                .toList();
        return BaseResponse.builder()
                .data(response)
                .message("find all orders by product")
                .success(true)
                .httpStatus(HttpStatus.OK).build();
    }

    private GetProductResponse from(ProductProjection product) {
        return productServices.findResponseById(product.getId());
    }

    private GetOrderResponse from(OrderProjection order) {
        return orderServices.findResponseById(order.getId());
    }

    private OrderProduct from(CreateOrderProductRequest request) {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setOrder(orderServices.findById(request.getOrderId()));
        orderProduct.setProduct(productServices.findById(request.getProductId()));
        return orderProduct;
    }

    private GetOrderProductResponse from(OrderProduct orderProduct) {
        return GetOrderProductResponse.builder()
                .id(orderProduct.getId())
                .product(productServices.findResponseById(orderProduct.getProduct().getId()))
                .order(orderServices.findResponseById(orderProduct.getOrder().getId()))
                .build();
    }
}
