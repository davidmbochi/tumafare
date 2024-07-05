package com.tumafare.com.tumafare.stkpush.stkpushstatus;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class MpesaStkPushStatusHandler {
    private static final String PAYMENT_STATUS_URL = "https://payment.intasend.com/api/v1/payment/status/";
    private final RestTemplate restTemplate;

    @Value("${instasend.mpesa.api-secret}")
    private String API_SECRET;

    public MpesaStkPushStatusResponse mpesaStkPushStatus(String invoiceId) {
        MpesaStkPushStatusRequest mpesaStkPushStatusRequest =
                new MpesaStkPushStatusRequest(invoiceId);

        HttpHeaders headers = getHeaders();

        HttpEntity<MpesaStkPushStatusRequest> httpEntity =
                new HttpEntity<>(mpesaStkPushStatusRequest, headers);

        return restTemplate.exchange(
                PAYMENT_STATUS_URL,
                HttpMethod.POST,
                httpEntity,
                MpesaStkPushStatusResponse.class
        ).getBody();
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Content-Type", "application/json");
        headers.setBearerAuth(API_SECRET);
        return headers;
    }
}
