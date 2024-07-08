package com.tumafare.com.tumafare.stkpush;

import com.tumafare.com.tumafare.payment.PaymentService;
import com.tumafare.com.tumafare.random.UniqueStringService;
import com.tumafare.com.tumafare.sms.MoveSmsService;
import com.tumafare.com.tumafare.stkpush.sale.SaleMapper;
import com.tumafare.com.tumafare.stkpush.stkpushstatus.MpesaStatus;
import com.tumafare.com.tumafare.stkpush.stkpushstatus.MpesaStkPushStatusResponse;
import com.tumafare.com.tumafare.stkpush.stkpushstatus.MpesaStkPushStatusHandler;
import com.tumafare.com.tumafare.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.tumafare.com.tumafare.payment.PaymentStatus.APPROVED;
import static com.tumafare.com.tumafare.payment.PaymentStatus.PENDING;
import static com.tumafare.com.tumafare.stkpush.stkpushstatus.MpesaStatus.*;

@Service
@RequiredArgsConstructor
public class MpesaStkPushService {
    private final MpesaStkPushHandler mpesaStkPushHandler;
    private final MpesaStkPushStatusHandler mpesaStkPushStatusHandler;
    private final MpesaStkPushRepository repository;
    private final MpesaStkPushMapper mapper;
    private final MoveSmsService moveSmsService;
    private final UniqueStringService stringService;
    private final SaleMapper saleMapper;


    public MpesaStkPushStatusResponse makePayment(
            MpesaStkPushRequest mpesaStkPushRequest,
            Authentication connectedUser) throws InterruptedException {

        if (mpesaStkPushRequest.getUserPhoneNumber().equals(mpesaStkPushRequest.getClientPhoneNumber())){
            throw new RuntimeException("You cannot send to yourself");
        }

        MpesaStkPushResponse response = mpesaStkPushHandler.makePayment(mpesaStkPushRequest);

        String invoiceId = response.getInvoice().getInvoiceId();

        Thread.sleep(1000);

        MpesaStkPushStatusResponse statusResponse =
                mpesaStkPushStatusHandler.mpesaStkPushStatus(invoiceId);

        String currentStatus = statusResponse.getInvoice().getState();

        while (currentStatus.equals(MpesaStatus.PENDING.getStatus()) || currentStatus.equals(PROCESSING.getStatus())){
            Thread.sleep(1000);
            statusResponse = mpesaStkPushStatusHandler.mpesaStkPushStatus(invoiceId);

            currentStatus = statusResponse.getInvoice().getState();

            if (!Objects.equals(currentStatus, MpesaStatus.PENDING.getStatus()) && !Objects.equals(currentStatus, PROCESSING.getStatus())){
                MpesaStkPush mpesaStkPush = mapper.toMpesaStkPush(mpesaStkPushRequest);
                if (currentStatus.equals(COMPLETE.getStatus())){
                    mpesaStkPush.setSale(saleMapper.toSale(calculateSales(mpesaStkPush, statusResponse)));
                    mpesaStkPush.setAmount(calculateAmount(mpesaStkPush, statusResponse));
                    updateStkPush(connectedUser, mpesaStkPush, currentStatus, invoiceId);
                    notifyUserAndClient(statusResponse, mpesaStkPush);
                }
                updateStkPush(connectedUser, mpesaStkPush, currentStatus, invoiceId);
                break;
            }
        }
        return statusResponse;
    }

    private void updateStkPush(Authentication connectedUser, MpesaStkPush mpesaStkPush, String currentStatus, String invoiceId) {
        User user = ((User) connectedUser.getPrincipal());
        mpesaStkPush.setMpesaStatus(currentStatus);
        mpesaStkPush.setInvoiceId(invoiceId);
        mpesaStkPush.setPaymentStatus(PENDING.getStatus());
        mpesaStkPush.setOwner(user);
        repository.save(mpesaStkPush);
    }

    private String calculateAmount(MpesaStkPush mpesaStkPush, MpesaStkPushStatusResponse statusResponse) {
        BigDecimal percentage = new BigDecimal("0.05");
        BigDecimal transFee = percentage.multiply(new BigDecimal(mpesaStkPush.getAmount()));

        BigDecimal initialAmount = new BigDecimal(mpesaStkPush.getAmount());

        BigDecimal newAmount = initialAmount.subtract(transFee).subtract(BigDecimal.valueOf(statusResponse.getInvoice().getCharges()));

        return newAmount.toString();
    }

    private String calculateSales(MpesaStkPush mpesaStkPush, MpesaStkPushStatusResponse statusResponse) {
        BigDecimal percentage = new BigDecimal("0.05");
        BigDecimal transFee = percentage.multiply(new BigDecimal(mpesaStkPush.getAmount()));

        BigDecimal initialAmount = new BigDecimal(mpesaStkPush.getAmount());

        BigDecimal newAmount = initialAmount.subtract(transFee).subtract(BigDecimal.valueOf(statusResponse.getInvoice().getCharges()));

        return initialAmount.subtract(newAmount).toString();
    }

    private void notifyUserAndClient(MpesaStkPushStatusResponse statusResponse, MpesaStkPush mpesaStkPush) {
        String userMessage = stringService.generateUniqueSmsID()+
                " Confirmed. We have received KES "
                + statusResponse.getInvoice().getValue()+" to "
                + mpesaStkPush.getClientPhoneNumber() +". Approve the payment now";
        moveSmsService.sendSMS(mpesaStkPush.getUserPhoneNumber(), userMessage);

        String clientMessage = stringService.generateUniqueSmsID()+ " Confirmed. You have received KES "
                + statusResponse.getInvoice().getValue()+" from "
                + mpesaStkPush.getUserPhoneNumber()+ ". You should receive it in your M-Pesa withing minutes";
        moveSmsService.sendSMS(mpesaStkPush.getClientPhoneNumber(), clientMessage);
    }

    public List<MpesaStkPush> getPaymentsByUserId(Integer userId) {
        return repository.findByOwnerId(userId);
    }

    public void refreshMpesa(String invoiceId, Integer paymentId, Authentication connectedUser) {
        MpesaStkPushStatusResponse mpesaStkPushStatusResponse = mpesaStkPushStatusHandler.mpesaStkPushStatus(invoiceId);
        User user = (User) connectedUser.getPrincipal();
        Optional<MpesaStkPush> mpesaStkPush = user.getStkPushes()
                .stream()
                .filter(stkPush -> stkPush.getId().equals(paymentId))
                .findFirst();

        if (mpesaStkPush.isPresent()){
            if (mpesaStkPushStatusResponse.getInvoice().getState().equals(COMPLETE.getStatus())) {
                mpesaStkPush.get().setMpesaStatus(mpesaStkPushStatusResponse.getInvoice().getState());
                repository.save(mpesaStkPush.get());
                notifyUserAndClient(mpesaStkPushStatusResponse, mpesaStkPush.get());
            }
            mpesaStkPush.get().setMpesaStatus(mpesaStkPushStatusResponse.getInvoice().getState());
            repository.save(mpesaStkPush.get());
        }

    }

    public MpesaStkPush findById(Integer paymentId) {
        return repository.findById(paymentId)
                .stream().findFirst()
                .orElseThrow(()-> new RuntimeException("Payment not found"));
    }

    public void deletePayment(Integer paymentId) {
        MpesaStkPush stkPush = repository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        repository.deleteById(stkPush.getId());
    }

    public void approveDisbursement(Integer paymentId) {
        MpesaStkPush stkPush = repository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        PaymentService.calculateNewAmount(stkPush);
        stkPush.setPaymentStatus(APPROVED.getStatus());
        repository.save(stkPush);

    }

    public void cancelDisbursement(Integer paymentId) {
        MpesaStkPush stkPush = repository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        PaymentService.calculateNewAmount(stkPush);
        stkPush.setPaymentStatus(CANCELED.getStatus());
        repository.save(stkPush);
    }

    public List<MpesaStkPush> findAll() {
        return repository.findAll();
    }
}
