package com.example.logs.demo.grafanaLoki.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.Currency;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Customer {

    private long customerId;

    private BigDecimal amount;
    private Currency currency;


    @Builder
    public Customer(long customerId, BigDecimal amount, Currency currency) {
        this.customerId = customerId;
        this.amount = amount;
        this.currency = currency;
    }
}
