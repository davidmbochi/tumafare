package com.tumafare.com.tumafare.payment;

import com.tumafare.com.tumafare.stkpush.MpesaStkPush;
import com.tumafare.com.tumafare.stkpush.MpesaStkPushRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.tumafare.com.tumafare.payment.PaymentStatus.COMPLETE;
import static com.tumafare.com.tumafare.payment.PaymentStatus.REVERSED;

@Service
@RequiredArgsConstructor
public class PaymentHandler {
    private final PaymentStatusService paymentStatusService;
    private final MpesaStkPushRepository repository;
    private static final String SEND_MONEY_INITIATE_URL = "https://payment.intasend.com/api/v1/send-money/initiate/";
    private final RestTemplate restTemplate;

    @Value("${instasend.mpesa.api-secret}")
    private String API_SECRET;

    public void disbursePayment(MpesaStkPush mpesaStkPush) throws InterruptedException {
        PaymentRequest request = new PaymentRequest(mpesaStkPush.getClientPhoneNumber(), mpesaStkPush.getAmount());

        HttpHeaders headers = getHeaders();

        HttpEntity<PaymentRequest> requestEntity = new HttpEntity<>(request, headers);

        PaymentResponse response = restTemplate.exchange(
                SEND_MONEY_INITIATE_URL,
                HttpMethod.POST,
                requestEntity,
                PaymentResponse.class
        ).getBody();

        if (response.getStatus().equals("Confirming balance")){
            if (mpesaStkPush.getPaymentStatus().equals(REVERSED.getStatus())){
                repository.save(mpesaStkPush);
            }else {
                mpesaStkPush.setPaymentStatus(COMPLETE.getStatus());
                repository.save(mpesaStkPush);
            }
        }

//        DisbursementStatusResponse disbursementStatusResponse = disbursementStatusService.disbursementStatusResponse(response.getTrackingId());
//
//        while (!disbursementStatusResponse.getStatus().equals("Completed")){
//            Thread.sleep(3000);
//            disbursementStatusResponse = disbursementStatusService.disbursementStatusResponse(response.getTrackingId());
//
//            if (disbursementStatusResponse.getStatus().equals("Completed")){
//                Optional<DisbursementStatusResponse.Transaction> paymentTransaction = disbursementStatusResponse.getTransactions()
//                        .stream()
//                        .filter(transaction -> transaction.getStatus().equals("Successful"))
//                        .findFirst();
//
//                if (paymentTransaction.isPresent()) {
//                    mpesaStkPush.setPaymentStatus(COMPLETE.getStatus());
//                    mpesaStkPush.setAmount("0.0");
//                    repository.save(mpesaStkPush);
//                }
//                break;
//            }
//        }
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Content-Type", "application/json");
        headers.setBearerAuth(API_SECRET);
        return headers;
    }
}
