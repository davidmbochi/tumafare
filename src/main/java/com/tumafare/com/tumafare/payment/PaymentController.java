package com.tumafare.com.tumafare.payment;

import com.tumafare.com.tumafare.stkpush.MpesaStkPush;
import com.tumafare.com.tumafare.stkpush.MpesaStkPushService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.tumafare.com.tumafare.payment.PaymentStatus.*;

@Controller
@RequestMapping("payment")
@RequiredArgsConstructor
public class PaymentController {
    private final MpesaStkPushService mpesaStkPushService;
    private final PaymentService paymentService;

    @GetMapping("/complete-payment/{paymentId}")
    public String completeDisbursement(@PathVariable("paymentId") Integer paymentId) throws InterruptedException {
        MpesaStkPush stkPush = mpesaStkPushService.findById(paymentId);
        paymentService.completeDisbursement(stkPush);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/reverse-payment/{paymentId}")
    public String reverseDisbursement(@PathVariable("paymentId") Integer paymentId) throws InterruptedException {
        MpesaStkPush stkPush = mpesaStkPushService.findById(paymentId);
        stkPush.setClientPhoneNumber(stkPush.getUserPhoneNumber());
        stkPush.setPaymentStatus(REVERSED.getStatus());
        paymentService.completeDisbursement(stkPush);
        return "redirect:/admin/dashboard";
    }
}
