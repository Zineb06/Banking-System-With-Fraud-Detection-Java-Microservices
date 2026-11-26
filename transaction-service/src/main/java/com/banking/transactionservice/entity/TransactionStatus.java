package com.banking.transactionservice.entity;

/**
 * TransactionStatus / LIFECYCLE FLOW
 * pending > processing > completed (money credited to receiver)
 * .... > pending_verification (suspicious detected)
 * > completed (verified)
 * > flagged (SAGA REFUND)
 * > FAILED
 */
public enum TransactionStatus {
    PENDING,
    PROCESSING,
    PENDING_VERIFICATION,
    COMPLETED,
    FLAGGED,
    FAILED
}
