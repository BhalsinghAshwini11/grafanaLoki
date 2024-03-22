package com.example.logs.demo.grafanaLoki.controller;

import com.example.logs.demo.grafanaLoki.model.Customer;
import com.example.logs.demo.grafanaLoki.service.WalletService;
import com.example.logs.demo.grafanaLoki.util.MDCUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class BalanceController {
    private static final Logger logger = LoggerFactory.getLogger(BalanceController.class);
    private final WalletService walletService;

    public BalanceController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/addCustomer")
    public Customer createCustomer(@RequestParam Long customerId) {
        MDCUtil.setMDC(String.valueOf(customerId));
        Customer cus = null;
        logger.info("Creating new customer={} wallet", customerId);
        try {
            cus = walletService.createCustomerWallet(customerId);
        } catch (Exception e) {
            logger.error("NOK, customer not created");
        } finally {
            MDC.clear();
        }
        return cus;
    }

    @GetMapping("/balance")
    public BigDecimal getBalance(@RequestParam Long customerId) throws Exception {
        MDCUtil.setMDC(String.valueOf(customerId));
        logger.info("Balance request for customer: {}", customerId);
        BigDecimal balance = walletService.getBalance(customerId);
        MDC.clear();
        return balance;
    }

    @PostMapping("/deposit")
    public void deposit(@RequestParam Long customerId, @RequestParam BigDecimal amount) {
        MDCUtil.setMDC(String.valueOf(customerId));
        logger.info("Deposit req for customer={}, amount={}", customerId, amount);
        verifyInput(customerId, amount);
        walletService.deposit(customerId, amount);
        MDC.clear();
    }

    @PostMapping("/withdraw")
    public void withdraw(@RequestParam Long customerId, @RequestParam BigDecimal amount) {
        MDCUtil.setMDC(String.valueOf(customerId));
        logger.info("Withdraw req for customer={}, amount={}", customerId, amount);
        verifyInput(customerId, amount);
        walletService.withdraw(customerId, amount);
        MDC.clear();
    }

    private void verifyInput(Long customerId, BigDecimal amount) {
        assert customerId.compareTo(0L) != 0;
        assert amount.compareTo(BigDecimal.ZERO) > 0;
    }
}
