package com.tumafare.com.tumafare.stkpush.stkpushstatus;

import lombok.Getter;

@Getter
public enum MpesaStatus {
    PENDING("PENDING"),
    PROCESSING("PROCESSING"),
    FAILED("FAILED"),
    CANCELED("CANCELED"),
    PARTIAL("PARTIAL"),
    COMPLETE("COMPLETE"),
    RETRY("RETRY");

    private final String status;

    MpesaStatus(String status) {
        this.status = status;
    }
}
