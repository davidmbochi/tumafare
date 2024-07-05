package com.tumafare.com.tumafare.payment;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    PENDING("PENDING"),
    APPROVED("APPROVED"),
    CANCELED("CANCELED"),
    COMPLETE("COMPLETE"),
    REVERSED("REVERSED");

    private String status;

    PaymentStatus(String status) {
        this.status = status;
    }
}
