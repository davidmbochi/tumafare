package com.tumafare.com.tumafare.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentStatusService {
    private final PaymentStatusHandler paymentStatusHandler;

    public PaymentStatusResponse disbursementStatusResponse(String trackingId){
        return paymentStatusHandler.disbursementStatusResponse(trackingId);
    }
}
