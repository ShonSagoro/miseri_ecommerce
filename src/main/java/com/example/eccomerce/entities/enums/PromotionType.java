package com.example.eccomerce.entities.enums;

import lombok.Getter;

@Getter
public enum PromotionType {
    MOUNT("mount"),
    PERCENTAJE("percentaje");

    private final String type;

    PromotionType(String type) {
        this.type = type;
    }
}
