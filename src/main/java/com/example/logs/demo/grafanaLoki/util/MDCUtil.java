package com.example.logs.demo.grafanaLoki.util;

import org.slf4j.MDC;

import java.util.UUID;

import static com.example.logs.demo.grafanaLoki.util.MDCConstants.CUSTOMER_ID;

public class MDCUtil {
    public static void setMDC(String customerId) {
        String X_Request_ID = UUID.randomUUID().toString(); //Fixme, demo purpose generating it here
        MDC.put(CUSTOMER_ID, customerId);
        MDC.put(X_Request_ID, X_Request_ID);
    }
}
