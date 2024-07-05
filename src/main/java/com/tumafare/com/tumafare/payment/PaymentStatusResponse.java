package com.tumafare.com.tumafare.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PaymentStatusResponse {
    @JsonProperty("file_id")
    private String fileId;
    @JsonProperty("tracking_id")
    private String trackingId;
    @JsonProperty("batch_reference")
    private String batchReference;
    @JsonProperty("status")
    private String status;
    @JsonProperty("status_code")
    private String statusCode;
    @JsonProperty("transactions")
    private List<Transaction> transactions;
    @JsonProperty("actual_charges")
    private String actualCharges;
    @JsonProperty("paid_amount")
    private String paidAmount;
    @JsonProperty("failed_amount")
    private double failedAmount;
    @JsonProperty("wallet")
    private Wallet wallet;
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
    public static class Transaction {
        @JsonProperty("transaction_id")
        private String transactionId;
        @JsonProperty("status")
        private String status;
        @JsonProperty("status_code")
        private String statusCode;
        @JsonProperty("request_reference_id")
        private String requestReferenceId;
        @JsonProperty("provider")
        private String provider;
        @JsonProperty("bank_code")
        private String bankCode;
        @JsonProperty("name")
        private String name;
        @JsonProperty("account")
        private String account;
        @JsonProperty("account_type")
        private String accountType;
        @JsonProperty("account_reference")
        private String accountReference;
        @JsonProperty("provider_reference")
        private String providerReference;
        @JsonProperty("provider_account_name")
        private String providerAccountName;
        @JsonProperty("amount")
        private String amount;
        @JsonProperty("charge")
        private String charge;
        @JsonProperty("narrative")
        private String narrative;
        @JsonProperty("file_id")
        private String fileId;
        @JsonProperty("currency")
        private String currency;


    }

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
}
