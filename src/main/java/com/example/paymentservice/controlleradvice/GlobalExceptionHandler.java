package com.example.paymentservice.controlleradvice;

import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RazorpayException.class)
    public ResponseEntity<String> handleRazorpayException(){
        ResponseEntity<String> response = new ResponseEntity<>(
            "razorpay could not generate payment link Pls try again!!",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
        return response;
    }

    @ExceptionHandler(StripeException.class)
    public ResponseEntity<String> handleStripeException(){
        ResponseEntity<String> response = new ResponseEntity<>(
                "stripe could not generate payment link Pls try again!!",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
        return response;
    }

}
