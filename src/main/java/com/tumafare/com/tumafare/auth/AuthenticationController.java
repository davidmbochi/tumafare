package com.tumafare.com.tumafare.auth;

import com.tumafare.com.tumafare.auth.password.ForgotPasswordEmail;
import com.tumafare.com.tumafare.auth.password.ForgotPasswordService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    private final ForgotPasswordService forgotPasswordService;

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("regRequest", new RegistrationRequest());
        return "auth/register";
    }

    @PostMapping("/process-registration")
    public String processRegistration(@ModelAttribute("regRequest") @Valid RegistrationRequest request,
                                      BindingResult result) throws MessagingException {
        if (result.hasErrors()){
            return "auth/register";
        }
        service.register(request);
        return "redirect:/auth/activate";
    }

    @GetMapping("/activate")
    public String activate(Model model){
        model.addAttribute("submitted", false);
        return "auth/activate";
    }

    @GetMapping("/activate-account")
    public String confirm(@RequestParam String token, Model model){
        try {
            service.activateAccount(token);
            model.addAttribute("message", "Your account has been successfully activated. Now you can proceed to login");
            model.addAttribute("submitted", true);
            model.addAttribute("isOkay", true);
        }catch (Exception e){
            model.addAttribute("message", "Token has expired or invalid");
            model.addAttribute("submitted", true);
            model.addAttribute("isOkay", false);
        }
        return "auth/activate";
    }

    @GetMapping("/login")
    public String authenticate(){
        return "auth/login";
    }

}
