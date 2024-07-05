package com.tumafare.com.tumafare.admin;

import com.tumafare.com.tumafare.stkpush.MpesaStkPushService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("admin")
public class AdminController {

    private final MpesaStkPushService mpesaStkPushService;

    @GetMapping
    public String admin(Model model){
        model.addAttribute("payments", mpesaStkPushService.findAll());
        return "payment/payment-dashboard";
    }
}
