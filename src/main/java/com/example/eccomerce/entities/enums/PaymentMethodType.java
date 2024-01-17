package com.example.eccomerce.entities.enums;

import lombok.Getter;

@Getter
public enum PaymentMethodType {
    STRIPE("Stripe");

    private final String type;

    PaymentMethodType(String type){
        this.type = type;
    }
}
