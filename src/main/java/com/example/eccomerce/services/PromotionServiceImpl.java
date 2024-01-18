package com.example.eccomerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.example.eccomerce.controllers.dtos.request.CreatePromotionRequest;
import com.example.eccomerce.controllers.dtos.request.UpdatePromotionRequest;
import com.example.eccomerce.controllers.dtos.response.BaseResponse;
import com.example.eccomerce.controllers.dtos.response.GetPromotionResponse;
import com.example.eccomerce.entities.Promotion;
import com.example.eccomerce.entities.enums.converters.PromotionTypeConverter;
import com.example.eccomerce.repositories.IPromotionRepository;
import com.example.eccomerce.services.interfaces.IPromotionServices;
import org.springframework.stereotype.Service;

@Service
public class PromotionServiceImpl implements IPromotionServices {

    @Autowired
    private IPromotionRepository repository;

    @Autowired
    private PromotionTypeConverter converter;

    @Override
    public BaseResponse create(CreatePromotionRequest request) {
        Promotion promotion = repository.save(from(request));
        return BaseResponse.builder()
                .data(from(promotion))
                .message("The promotion was created")
                .success(true)
                .httpStatus(HttpStatus.CREATED).build();
    }

    @Override
    public BaseResponse update(Long id, UpdatePromotionRequest request) {
        Promotion promotion = findById(id);
        promotion = repository.save(update(promotion, request));
        return BaseResponse.builder()
                .data(from(promotion))
                .message("The promotion was updated")
                .success(true)
                .httpStatus(HttpStatus.OK).build();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public BaseResponse list() {
        List<GetPromotionResponse> responses = repository
                .findAll()
                .stream()
                .map(this::from).toList();
        return BaseResponse.builder()
                .data(responses)
                .message("find all promotions")
                .success(true)
                .httpStatus(HttpStatus.FOUND).build();
    }

    @Override
    public BaseResponse get(Long id) {
        Promotion promotion = repository.findById(id)
                .orElseThrow(RuntimeException::new);
        return BaseResponse.builder()
                .data(from(promotion))
                .message("promotion for: " + id)
                .success(true)
                .httpStatus(HttpStatus.FOUND).build();
    }

    @Override
    public Promotion findById(Long id) {
        return repository.findById(id)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public GetPromotionResponse findResponseById(Long id) {
        return from(repository.findById(id)
                .orElseThrow(RuntimeException::new));
    }

    private GetPromotionResponse from(Promotion promotion) {
        GetPromotionResponse response = new GetPromotionResponse();
        response.setId(promotion.getId());
        response.setName(promotion.getName());
        response.setDescription(promotion.getDescription());
        response.setValue(promotion.getValue());
        response.setType(converter.convertToDatabaseColumn(promotion.getType()));
        return response;

    }

    private Promotion from(CreatePromotionRequest request) {
        Promotion promotion = new Promotion();
        promotion.setName(request.getName());
        promotion.setDescription(request.getDescription());
        promotion.setValue(request.getValue());
        promotion.setType(converter.convertToEntityAttribute(request.getType()));
        return promotion;
    }

    private Promotion update(Promotion promotion, UpdatePromotionRequest update) {
        promotion.setName(update.getName());
        promotion.setDescription(update.getDescription());
        promotion.setValue(update.getValue());
        promotion.setType(converter.convertToEntityAttribute(update.getType()));
        return promotion;
    }

}
