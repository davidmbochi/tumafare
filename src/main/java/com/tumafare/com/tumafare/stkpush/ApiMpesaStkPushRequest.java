package com.tumafare.com.tumafare.stkpush;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class ApiMpesaStkPushRequest {
    @JsonProperty("amount")
    private String amount;
    @JsonProperty("phone_number")
    private String phoneNumber;
}
