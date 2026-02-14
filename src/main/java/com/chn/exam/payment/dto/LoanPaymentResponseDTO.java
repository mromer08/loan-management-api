package com.chn.exam.payment.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

import com.chn.exam.payment.model.PaymentMethod;

public record LoanPaymentResponseDTO(
        UUID id,
        UUID loanId,
        UUID customerId,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        LocalDateTime paymentDate,
        String notes,
        Instant createdAt,
        Instant updatedAt) {
}
