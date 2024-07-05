package com.tumafare.com.tumafare.auth.password;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordEmail {
    @NotBlank(message = "Email should not be blank")
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be well formated")
    private String email;
}
