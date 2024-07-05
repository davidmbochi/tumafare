package com.tumafare.com.tumafare.stkpush;

import org.springframework.stereotype.Service;

@Service
public class MpesaStkPushMapper {

    public ApiMpesaStkPushRequest toMpesaStkPushRequest(MpesaStkPushRequest request) {
        return ApiMpesaStkPushRequest.builder()
                .amount(request.getAmount())
                .phoneNumber(request.getUserPhoneNumber())
                .build();
    }

    public MpesaStkPush toMpesaStkPush(MpesaStkPushRequest mpesaStkPushRequest) {
        return MpesaStkPush.builder()
                .amount(mpesaStkPushRequest.getAmount())
                .userPhoneNumber(mpesaStkPushRequest.getUserPhoneNumber())
                .clientPhoneNumber(mpesaStkPushRequest.getClientPhoneNumber())
                .isPaymentApproved(false)
                .build();

    }
}
