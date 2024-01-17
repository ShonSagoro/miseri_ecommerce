package com.example.eccomerce.entities.enums.converters;

import org.springframework.stereotype.Component;

import com.example.eccomerce.entities.enums.PromotionType;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.stream.Stream;

@Component
@Converter(autoApply = true)
public class PromotionTypeConverter implements AttributeConverter<PromotionType, String> {

    @Override
    public String convertToDatabaseColumn(PromotionType promotionType) {
        if (promotionType == null)
            return null;
        return promotionType.getType();
    }

    @Override
    public PromotionType convertToEntityAttribute(String type) {
        if (type == null)
            return null;
        return Stream.of(PromotionType.values())
                .filter(t -> t.getType().equals(type))
                .findFirst().orElseThrow(RuntimeException::new);
    }
}
