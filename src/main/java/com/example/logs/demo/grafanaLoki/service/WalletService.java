package com.example.logs.demo.grafanaLoki.service;

import com.example.logs.demo.grafanaLoki.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

/*
Dummy wallet service with bugs, for customer creation and balance handling!
* * */
@Service
public class WalletService {

    private static final Logger logger = LoggerFactory.getLogger(WalletService.class);
    private final List<Customer> customerList = new ArrayList<>();


    public Customer createCustomerWallet(Long customerId) {
        logger.info("Creating customer");
        Customer customer = new Customer(customerId, BigDecimal.ZERO, Currency.getInstance("SEK"));
        customerList.add(customer);
        logger.info("Created new wallet successfully for customerId={}", customerId);
        return customer;
    }

    public BigDecimal getBalance(Long customerId) throws Exception {
        if (customerList.isEmpty()) {
            throw new Exception("Customer do not exist");
        } else {
            Customer customer = findCustomer(customerId);
            BigDecimal amount = customer.getAmount();
            logger.info("Balance of customer={}, balance={}", customerId, amount);
            return amount;
        }
    }

    private Customer findCustomer(Long customerId) {
        Customer customer = customerList.stream()
                .filter(c -> customerId.compareTo(c.getCustomerId()) == 0)
                .findFirst().orElseThrow();
        return customer;
    }

    public void deposit(Long customerId, BigDecimal amount) {
        logger.info("Fake Depositing amount={} for customerId={}", amount, customerId);
    }

    public void withdraw(Long customerId, BigDecimal amount) {
        logger.info("Fake Withdrawing amount={} for customerId={}", amount, customerId);
    }
}
