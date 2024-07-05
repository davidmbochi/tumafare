package com.tumafare.com.tumafare.stkpush.stkpushstatus;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MpesaStkPushStatusService {

    private final MpesaStkPushStatusHandler mpesaStkPushStatusHandler;

    public MpesaStkPushStatusResponse mpesaStkPushStatus(String invoiceId){
        return mpesaStkPushStatusHandler.mpesaStkPushStatus(invoiceId);
    }
}
