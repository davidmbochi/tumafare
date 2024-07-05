package com.tumafare.com.tumafare.sms;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;

import static java.nio.charset.StandardCharsets.*;

@Service
@RequiredArgsConstructor
public class MoveSmsService {
    private final RestTemplate restTemplate;

    private static final String BASE_URL = "https://sms.movesms.co.ke/api/compose";
    private static final String USERNAME = "DavidMbochi";

    @Value("${movesms.sms.api_key}")
    private String API_KEY;

    private static final String SENDER = "SMARTLINK";

    private static final String MESSAGE_TYPE = "5";
    private static final String DIR = "0";

    public String sendSMS(String to, String message) {
        String URL = String.format("%s?username=%s&api_key=%s&sender=%s&to=%s&message=%s&msgtype=%s&dlr=%s",
                BASE_URL, USERNAME, API_KEY, SENDER, to, URLEncoder.encode(message, UTF_8), MESSAGE_TYPE, DIR);
        System.out.println(URL);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        headers.add("Cache-Control", "no-cache");
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(
                URL,
                HttpMethod.POST,
                requestEntity,
                String.class
        ).getBody();
    }

}
