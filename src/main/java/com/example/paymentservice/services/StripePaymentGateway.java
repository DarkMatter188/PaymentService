package com.example.paymentservice.services;

import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("stripePaymentGateway")
public class StripePaymentGateway implements PaymentService {

    @Value("${stripe.api.key}")
    private String stripeApiKey;
    //make call to stripe PG

    @Override
    public String generatePaymentLink(Long orderId) throws RazorpayException, StripeException {
        Stripe.apiKey = stripeApiKey;

        PriceCreateParams priceCreateParams =
                PriceCreateParams.builder()
                        .setCurrency("INR")
                        .setUnitAmount(10000L)
                        .setRecurring(
                                PriceCreateParams.Recurring.builder()
                                        .setInterval(PriceCreateParams.Recurring.Interval.MONTH)
                                        .build()
                        )
                        .setProductData(
                                PriceCreateParams.ProductData.builder().setName("Buy Silk Chocolate").build()
                        )
                        .build();
        Price price = Price.create(priceCreateParams);




        PaymentLinkCreateParams paymentLinkCreateParams =
                PaymentLinkCreateParams.builder()
                        .addLineItem(
                                PaymentLinkCreateParams.LineItem.builder()
                                        .setPrice(price.getId())
                                        .setQuantity(1L)
                                        .build()
                        )
                        .setAfterCompletion(
                                PaymentLinkCreateParams.AfterCompletion.builder()
                                        .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT) // Callback
                                        .setRedirect(PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
                                                .setUrl("https://www.google.com")
                                                .build()
                                        )
                                        .build()

                        )
                        .build();

        PaymentLink paymentLink = PaymentLink.create(paymentLinkCreateParams);


        return paymentLink.getUrl();
    }
}
