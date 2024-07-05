package com.tumafare.com.tumafare.auth.password;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("password")
@RequiredArgsConstructor
public class ForgotPasswordController {
    private final ForgotPasswordService passwordService;

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model){
        model.addAttribute("password", new ForgotPasswordEmail());
        model.addAttribute("submitted", false);
        return "auth/forgot-password";
    }

    @PostMapping("/process-forgot-password")
    public String processForgotPassword(@Valid @ModelAttribute("password") ForgotPasswordEmail forgotPasswordEmail,
                                        BindingResult result,
                                        Model model) throws MessagingException {
        if (result.hasErrors()){
            model.addAttribute("submitted", false);
            return "auth/forgot-password";
        }
        passwordService.sendForgotPasswordEmail(forgotPasswordEmail.getEmail());
        model.addAttribute("submitted", true);
        return "auth/forgot-password";
    }

    @GetMapping("/change/{email}")
    public String Password(@PathVariable("email") String email,  Model model) {
        model.addAttribute("submitted", false);
        model.addAttribute("password", new Password());
        model.addAttribute("email", email);
        return "auth/change-password";
    }

    @PostMapping("/process-change-password")
    public String processChangePassword(
            @RequestParam("email") String email,
            @Valid @ModelAttribute("password") Password password,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()){
            model.addAttribute("submitted", false);
            model.addAttribute("email", email);
            return "auth/change-password";
        }
        passwordService.changePassword(email, password.getPassword());
        model.addAttribute("submitted", true);
        return "auth/change-password";
    }
}
