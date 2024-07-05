package com.tumafare.com.tumafare.stkpush;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class MpesaStkPushHandler {
    private static final String MPESA_STK_PUSH_URL = "https://payment.intasend.com/api/v1/payment/mpesa-stk-push/";

    private final RestTemplate restTemplate;
    private final MpesaStkPushMapper mapper;

    @Value("${instasend.mpesa.api-secret}")
    private String API_SECRET;

    public MpesaStkPushResponse makePayment(MpesaStkPushRequest request){
        ApiMpesaStkPushRequest mpesaStkPushRequest = mapper.toMpesaStkPushRequest(request);

        HttpHeaders headers = getHeaders();

        HttpEntity<ApiMpesaStkPushRequest> httpEntity = new HttpEntity<>(mpesaStkPushRequest, headers);

        return  restTemplate.exchange(
                MPESA_STK_PUSH_URL,
                HttpMethod.POST,
                httpEntity,
                MpesaStkPushResponse.class
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


