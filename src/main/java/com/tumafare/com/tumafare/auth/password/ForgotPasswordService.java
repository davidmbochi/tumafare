package com.tumafare.com.tumafare.auth.password;

import com.tumafare.com.tumafare.email.EmailService;
import com.tumafare.com.tumafare.email.EmailTemplateName;
import com.tumafare.com.tumafare.user.User;
import com.tumafare.com.tumafare.user.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ForgotPasswordService {
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Value("${application.password.change.change-password-url}")
    private String changePasswordUrl;

    public void sendForgotPasswordEmail(String email) throws MessagingException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        emailService.sendForgotPasswordEmail(
                user.getEmail(),
                user.getFirstname()+" "+user.getLastname(),
                EmailTemplateName.FORGOT_PASSWORD,
                changePasswordUrl+"/"+user.getEmail(),
                "Change Password"
        );

    }

    public void changePassword(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }
}
