package com.tumafare.com.tumafare.auth.password;

import com.tumafare.com.tumafare.validator.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.rmi.MarshalException;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Password {
    @NotBlank(message = "Password should not be blank")
    @NotEmpty(message = "Password should not be empty")
    @ValidPassword(message = "Password should be more than 12 characters, include numbers, special characters, upper case, and lower case letters.")
    private String password;
}
