package com.tumafare.com.tumafare.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaymentStatusRequest {
    @JsonProperty("tracking_id")
    private String trackingId;
}
