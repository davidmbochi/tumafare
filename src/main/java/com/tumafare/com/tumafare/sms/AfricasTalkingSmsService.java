package com.tumafare.com.tumafare.sms;

import com.africastalking.AfricasTalking;
import com.africastalking.SmsService;
import com.africastalking.sms.Recipient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AfricasTalkingSmsService {
    @Value("${africastalking.sms.api_key}")
    private String apiKey;
    private final String username = "javawhizz";

    public String sendSms(String to, String message) throws IOException {
        AfricasTalking.initialize(username, apiKey);

        SmsService smsService = AfricasTalking.getService(AfricasTalking.SERVICE_SMS);
        List<Recipient> response = smsService
                .send(message, new String[]{"+" + to}, true);
        System.out.println(response);
        return response.get(0).status;
    }
}
