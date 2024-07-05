package com.tumafare.com.tumafare.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class PaymentStatusHandler {
    private static final String SEND_MONEY_STATUS_URL = "https://payment.intasend.com/api/v1/send-money/status/";
    private final RestTemplate restTemplate;

    @Value("${instasend.mpesa.api-secret}")
    private String API_SECRET;

    public PaymentStatusResponse disbursementStatusResponse(String trackingId) {
        PaymentStatusRequest paymentStatusRequest = new PaymentStatusRequest(trackingId);

        HttpHeaders headers = getHeaders();
        HttpEntity<PaymentStatusRequest> httpEntity = new HttpEntity<>(paymentStatusRequest, headers);

        return restTemplate.exchange(
                SEND_MONEY_STATUS_URL,
                HttpMethod.POST,
                httpEntity,
                PaymentStatusResponse.class
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
