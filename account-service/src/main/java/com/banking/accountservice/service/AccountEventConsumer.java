package com.banking.accountservice.service;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// where we r going to consume 2 events : transaction completed event and fraud detection event

@Service // service layer class
@Slf4j // enable logging in the class
@RequiredArgsConstructor // generate constructor for final fields because we r using constructor
                         // injection and final fields for dependency injection
public class AccountEventConsumer {

    private final AccountService accountService;

    /**
     * consume transaction completed event from kafka
     * credits reeiver account
     * 
     * @param payload
     *                trans completed => no fraud + credit receiver
     */

    @KafkaListener(topics = "transaction_completed") // listen to transaction completed topic
    public void consumeTransactionCompleted(@Payload Map<String, Object> payload) {
        try {
            String receiverAccount = (String) payload.get("receiverAccountNumber");
            BigDecimal amount = new BigDecimal(payload.get("amount").toString());
            log.info("Crediting account {} with amount {}", receiverAccount, amount);
            accountService.creditBalance(receiverAccount, amount);

        } catch (Exception e) {
            log.error("error crediting account : {}", e.getMessage());
        }
    }

    /**
     * consume fraud detected event from kafka
     * block the flagged account
     * 
     * @param payload the payload containing the account number of the fraudulent
     *                account
     */
    public void consumeFraudDetected(@Payload Map<String, Object> payload) {
        try {
            String accountNumber = (String) payload.get("accountNumber");
            log.info("fraud detected.. blocking the account : {}", accountNumber);
            accountService.blockAccount(accountNumber);

        } catch (Exception e) {
            log.error("error blocking the account: {} ", e.getMessage());
        }
    }

}
