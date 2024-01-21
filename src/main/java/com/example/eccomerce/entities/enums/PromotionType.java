package com.example.eccomerce.entities.enums;

import lombok.Getter;

@Getter
public enum PromotionType {
    AMOUNT("amount"),
    PERCENTAGE("percentage");

    private final String type;

    PromotionType(String type) {
        this.type = type;
    }
}
