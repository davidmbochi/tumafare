package com.tumafare.com.tumafare.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaymentResponse {
    @JsonProperty("file_id")
    private String fileId;
    @JsonProperty("device_id")
    private String deviceId;
    @JsonProperty("tracking_id")
    private String trackingId;
    @JsonProperty("batch_reference")
    private String batchReference;
    @JsonProperty("status")
    private String status;
    @JsonProperty("status_code")
    private String statusCode;
    @JsonProperty("nonce")
    private String nonce;
    @JsonProperty("wallet")
    private Wallet wallet;
    @JsonProperty("transactions")
    private List<Transaction> transactions;
    @JsonProperty("charge_estimate")
    private double chargeEstimate;
    @JsonProperty("total_amount_estimate")
    private double totalAmountEstimate;
    @JsonProperty("total_amount")
    private double totalAmount;
    @JsonProperty("transactions_count")
    private int transactionsCount;


    @Getter
    @Setter
    public static class Wallet {
        @JsonProperty("wallet_id")
        private String walletId;
        @JsonProperty("label")
        private String label;
        @JsonProperty("can_disburse")
        private boolean canDisburse;
        @JsonProperty("currency")
        private String currency;
        @JsonProperty("wallet_type")
        private String walletType;
        @JsonProperty("current_balance")
        private double currentBalance;
        @JsonProperty("available_balance")
        private double availableBalance;

    }

    @Getter
    @Setter
    public static class Transaction {
        @JsonProperty("status")
        private String status;
        @JsonProperty("status_code")
        private String statusCode;
        @JsonProperty("request_reference_id")
        private String requestReferenceId;
        @JsonProperty("name")
        private String name;
        @JsonProperty("account")
        private String account;
        @JsonProperty("id_number")
        private String idNumber;
        @JsonProperty("bank_code")
        private String bankCode;
        @JsonProperty("amount")
        private double amount;
        @JsonProperty("narrative")
        private String narrative;

    }
}
