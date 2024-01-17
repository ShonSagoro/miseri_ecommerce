package com.example.eccomerce.entities.enums.converters;

import com.example.eccomerce.entities.enums.PaymentMethodType;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
@Converter(autoApply = true)
public class PaymentMethodTypeConverter implements AttributeConverter<PaymentMethodType, String> {

    @Override
    public String convertToDatabaseColumn(PaymentMethodType paymentMethodType) {
        if (paymentMethodType == null)
            return null;
        return paymentMethodType.getType();
    }

    @Override
    public PaymentMethodType convertToEntityAttribute(String type) {
        if (type == null)
            return null;
        return Stream.of(PaymentMethodType.values())
                .filter(t -> t.getType().equals(type))
                .findFirst().orElseThrow(RuntimeException::new);
    }
}
