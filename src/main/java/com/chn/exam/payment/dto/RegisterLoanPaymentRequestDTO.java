package com.chn.exam.payment.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.chn.exam.payment.model.PaymentMethod;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterLoanPaymentRequestDTO(
        @NotNull
        @DecimalMin("0.01")
        @Digits(integer = 16, fraction = 2)
        BigDecimal amount,

        @NotNull
        PaymentMethod paymentMethod,

        LocalDateTime paymentDate,

        @Size(max = 300)
        String notes) {
}
