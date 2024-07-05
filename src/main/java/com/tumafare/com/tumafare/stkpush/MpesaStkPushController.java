package com.tumafare.com.tumafare.stkpush;

import com.tumafare.com.tumafare.stkpush.stkpushstatus.MpesaStkPushStatusResponse;
import com.tumafare.com.tumafare.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("mpesa")
@RequiredArgsConstructor
public class MpesaStkPushController {
    private final MpesaStkPushService mpesaStkPushService;

    @GetMapping("/initiate-mpesa")
    public String initiatePayment(Model model) {
        model.addAttribute("stkRequest", new MpesaStkPushRequest());
        return "mpesa/payment";
    }

    @PostMapping("/process-mpesa")
    public String makePayment(
            @ModelAttribute("stkRequest") @Valid MpesaStkPushRequest stkRequest,
            BindingResult bindingResult,
            Model model,
            Authentication connectedUser) throws InterruptedException {

        if (bindingResult.hasErrors()) {
            return "mpesa/payment";
        }
        MpesaStkPushStatusResponse mpesaStkPushStatusResponse =
                mpesaStkPushService.makePayment(stkRequest, connectedUser);

        String state = mpesaStkPushStatusResponse.getInvoice().getState();
        model.addAttribute("paymentStatus", state);
        return "redirect:/mpesa/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        List<MpesaStkPush> payments = mpesaStkPushService.getPaymentsByUserId(user.getId());
        model.addAttribute("payments", payments);
        return "mpesa/mpesa-dashboard";
    }

    @GetMapping("/approve-mpesa/{paymentId}")
    public String approveDisbursement(@PathVariable("paymentId") Integer paymentId){
        mpesaStkPushService.approveDisbursement(paymentId);
        return "redirect:/mpesa/dashboard";
    }

    @GetMapping("/cancel-mpesa/{paymentId}")
    public String cancelDisbursement(@PathVariable("paymentId") Integer paymentId){
        mpesaStkPushService.cancelDisbursement(paymentId);
        return "redirect:/mpesa/dashboard";
    }

    @GetMapping("/refresh-mpesa/{invoiceId}/{paymentId}")
    public String refreshPayment(@PathVariable("invoiceId") String invoiceId,
                                 @PathVariable("paymentId") Integer paymentId,
                                 Authentication connectedUser) {
        System.out.println(invoiceId);
        mpesaStkPushService.refreshMpesa(invoiceId, paymentId, connectedUser);
        return "redirect:/mpesa/dashboard";
    }

    @GetMapping("/delete-mpesa/{paymentId}")
    public String deletePayment(@PathVariable("paymentId") Integer paymentId) {
        mpesaStkPushService.deletePayment(paymentId);
        return "redirect:/mpesa/dashboard";
    }

}
