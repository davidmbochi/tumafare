package com.tumafare.com.tumafare.admin;

import com.tumafare.com.tumafare.role.Role;
import com.tumafare.com.tumafare.stkpush.MpesaStkPushService;
import com.tumafare.com.tumafare.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;

@Controller
@RequiredArgsConstructor
@RequestMapping("admin")
public class AdminController {

    private final MpesaStkPushService mpesaStkPushService;

    @GetMapping("/dashboard")
    public String admin(Model model, Authentication connectedUser){
        User user = (User) connectedUser.getPrincipal();
        model.addAttribute("payments", mpesaStkPushService.findAll());
        model.addAttribute("username", user.getFirstname()+" "+user.getLastname());
        Role userRole = user.getRoles().stream()
                .min(Comparator.comparing(Role::getName))
                .get();
        model.addAttribute("role", userRole.getName());
        return "dashboard/admin-dashboard";
    }
}
