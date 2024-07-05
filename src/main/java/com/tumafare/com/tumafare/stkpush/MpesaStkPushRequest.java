 package com.tumafare.com.tumafare.stkpush;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MpesaStkPushRequest {
      @NotBlank(message = "Amount cannot be empty")
      @NotEmpty(message = "Amount is required")
      @Min(value = 30, message = "Enter a value greater or equal to 30")
      @Max(value = 150000, message = "Enter a value less than 150,000")
      String amount;

      @NotBlank(message = "User phone number cannot be empty")
      @NotEmpty(message = "User phone number is required")
//      @ValidNumber
      String userPhoneNumber;

      @NotBlank(message = "Client phone number cannot be empty")
      @NotEmpty(message = "Client phone is required")
//      @ValidNumber
      String clientPhoneNumber;

}
