package com.tumafare.com.tumafare.stkpush.stkpushstatus;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MpesaStkPushStatusRequest (
    @JsonProperty("invoice_id")
    String invoiceId
){

}
