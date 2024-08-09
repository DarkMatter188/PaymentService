package com.example.paymentservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class generatePaymentLinkRequestDto {
    private Long orderId;
}
