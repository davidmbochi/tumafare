package com.tumafare.com.tumafare.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PaymentRequest {
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("provider")
    private String provider;

    @JsonProperty("requires_approval")
    private String requiresApproval;

    @JsonProperty("transactions")
    private List<Transaction> transactions;

    public PaymentRequest(String account, String amount) {
        this.currency = "KES";
        this.provider = "MPESA-B2C";
        this.requiresApproval = "NO";
        this.transactions = Collections.singletonList(new Transaction(account, amount));

    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Transaction{
        @JsonProperty("account")
        private String account;
        @JsonProperty("amount")
        private String amount;
    }
}
