package com.tumafare.com.tumafare.stkpush;

import com.tumafare.com.tumafare.random.UniqueStringService;
import com.tumafare.com.tumafare.sms.AfricasTalkingSmsService;
import com.tumafare.com.tumafare.sms.MoveSmsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class MpesaStkPushServiceTest {

    @Autowired
    private MoveSmsService moveSmsService;

    @Autowired
    private UniqueStringService stringService;

    @Autowired
    private AfricasTalkingSmsService africasTalkingSmsService;

    @Test
    public void testSmsService(){
        String amount = "500";
        assertNotNull(moveSmsService.sendSMS("254737610119", "Test message "+amount+"You should  receive it in your M-pesa on approval"));
    }

    @Test
    public void testAfricasTalkingSms() throws IOException {
        String helloSafaricom = africasTalkingSmsService.sendSms("254743164072", "Hello Safaricom");
        assertNotNull(helloSafaricom);

        String helloAirtel = africasTalkingSmsService.sendSms("254737610119", "Hello Airtel");
        assertNotNull(helloAirtel);
    }
    @Test
    public void testRandomTextGen() {
        String randomText = stringService.generateUniqueSmsID();
        System.out.println(randomText);
        assertNotNull(randomText);
    }

}
