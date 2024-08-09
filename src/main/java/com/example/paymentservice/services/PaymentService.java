package com.example.paymentservice.services;


import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import org.springframework.stereotype.Service;

@Service
public interface PaymentService {
    public String generatePaymentLink(Long orderId) throws RazorpayException, StripeException;
}
