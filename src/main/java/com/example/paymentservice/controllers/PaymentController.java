package com.example.paymentservice.controllers;

import com.example.paymentservice.dtos.generatePaymentLinkRequestDto;
import com.example.paymentservice.services.PaymentService;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private PaymentService paymentService;

    public PaymentController(@Qualifier("stripePaymentGateway") PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public String generatePaymentLink(@RequestBody generatePaymentLinkRequestDto requestDto) throws RazorpayException, StripeException {
        //handle exception using controller advice
        return paymentService.generatePaymentLink(requestDto.getOrderId());
//        return null;
    }

    @PostMapping("/webhooks")
    public void handleWebhookEvent(@RequestBody Object object){

        System.out.println("Webhook got triggered!!");
    }

}
