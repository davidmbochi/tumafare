package com.tumafare.com.tumafare.payment;

import com.tumafare.com.tumafare.stkpush.MpesaStkPush;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentHandler paymentHandler;

    public void completeDisbursement(MpesaStkPush stkPush) throws InterruptedException {
        BigDecimal newAmount = calculateNewAmount(stkPush);
        stkPush.setAmount(newAmount.toString());
        paymentHandler.disbursePayment(stkPush);
    }

    public static BigDecimal calculateNewAmount(MpesaStkPush stkPush) {
        String chargeEstimate = new BigDecimal("10.00").toString();
        System.out.println("Charge estimate: " + chargeEstimate);
        BigDecimal amount = new BigDecimal(stkPush.getAmount());
        BigDecimal newAmount = amount.subtract(new BigDecimal(chargeEstimate));

        if (newAmount.compareTo(new BigDecimal("10.0")) < 0) {
            throw new RuntimeException("Charge estimate is KES 10, amount should be greater or equal to 10.0");
        }
        return newAmount;
    }
}
