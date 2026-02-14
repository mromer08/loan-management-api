package com.chn.exam.loan.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

import com.chn.exam.loan.model.LoanPaymentStatus;
import com.chn.exam.loan.model.LoanStatus;

public record LoanResponseDTO(
    UUID id,
    UUID customerId,
    String customerFullName,
    LocalDateTime loanDate,
    BigDecimal amount,
    Integer termMonths,
    String purpose,
    BigDecimal annualInterestRate,
    BigDecimal totalPayable,
    BigDecimal outstandingBalance,
    LoanPaymentStatus paymentStatus,
    LoanStatus status,
    Instant createdAt,
    Instant updatedAt
) {
}
