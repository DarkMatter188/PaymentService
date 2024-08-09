package com.example.paymentservice.services;

import com.example.paymentservice.configs.RazorpayClientConfig;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service("razorpayPaymentGateway")
public class RazorpayPaymentGateway implements PaymentService{

    private final RazorpayClient razorpayClient;
    //do constructor injection here

    public RazorpayPaymentGateway(RazorpayClient razorpayClient) {
        this.razorpayClient = razorpayClient;
    }

    @Override
    public String generatePaymentLink(Long orderId) throws RazorpayException {
        //Make API call from Razorpay here
        //need to create instance of razorpay but not many times so in configs package


        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount",1000);//amount is 10.00 always 2 places in decimal no round off
        paymentLinkRequest.put("currency","INR");
//        paymentLinkRequest.put("accept_partial",true);
//        paymentLinkRequest.put("first_min_partial_amount",100);

        paymentLinkRequest.put("expire_by",System.currentTimeMillis() + 10*60*1000);
        paymentLinkRequest.put("reference_id",orderId.toString());
        paymentLinkRequest.put("description","Payment for buying product from product service");
        JSONObject customer = new JSONObject();
        //get contact name from orderService
//        Order order = restTemplate.getForObject("Order URL", Order.class)
        customer.put("name","Apoorv Ranjan");
        customer.put("contact","+916201355894");
        customer.put("email","apoorvranjan343@gmail.com");
        paymentLinkRequest.put("customer",customer);
        JSONObject notify = new JSONObject();
        notify.put("sms",true);
        notify.put("email",true);
        paymentLinkRequest.put("reminder_enable",true);
        JSONObject notes = new JSONObject();
//        notes.put("policy_name","Jeevan Bima");
//        paymentLinkRequest.put("notes",notes);
        paymentLinkRequest.put("callback_url","https://google.com/");
        paymentLinkRequest.put("callback_method","get");

        PaymentLink payment = razorpayClient.paymentLink.create(paymentLinkRequest);

        return payment.get("short_url");
    }
}
