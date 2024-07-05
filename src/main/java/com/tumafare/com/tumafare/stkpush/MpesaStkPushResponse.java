package com.tumafare.com.tumafare.stkpush;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MpesaStkPushResponse {
    @JsonProperty("id")
    private String id;
    @JsonProperty("invoice")
    private Invoice invoice;
    @JsonProperty("customer")
    private Customer customer;
    @JsonProperty("payment_link")
    private String paymentLink;
    @JsonProperty("customer_comment")
    private String customerComment;
    @JsonProperty("refundable")
    private boolean refundable;

    @Getter
    @Setter
    public static class Invoice{
        @JsonProperty("invoice_id")
        private String invoiceId;
        @JsonProperty("state")
        private String state;
        @JsonProperty("provider")
        private String provider;
        @JsonProperty("charges")
        private double charges;
        @JsonProperty("net_amount")
        private String netAmount;
        @JsonProperty("currency")
        private String currency;
        @JsonProperty("value")
        private double value;
        @JsonProperty("account")
        private String account;
        @JsonProperty("api_ref")
        private String apiRef;
        @JsonProperty("mpesa_reference")
        private String mpesaReference;
        @JsonProperty("host")
        private String host;
        @JsonProperty("card_info")
        private CardInfo cardInfo;
        @JsonProperty("retry_count")
        private int retryCount;
        @JsonProperty("failed_reason")
        private String failedReason;
        @JsonProperty("failed_code")
        private String failedCode;
        @JsonProperty("failed_code_link")
        private String failedCodeLink;

        @Getter
        @Setter
        public static class CardInfo{
            @JsonProperty("bin_country")
            private String binCountry;
            @JsonProperty("card_type")
            private String cardType;
        }
    }

    @Getter
    @Setter
    public static class Customer{
        @JsonProperty("customer_id")
        private String customerId;
        @JsonProperty("phone_number")
        private String phoneNumber;
        @JsonProperty("email")
        private String email;
        @JsonProperty("first_name")
        private String firstName;
        @JsonProperty("last_name")
        private String lastName;
        @JsonProperty("country")
        private String country;
        @JsonProperty("zipcode")
        private String zipcode;
        @JsonProperty("provider")
        private String provider;
    }
}
