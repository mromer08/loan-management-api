package com.chn.exam.loan.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record SubmitLoanApplicationRequestDTO(
    LocalDateTime loanDate,

    @NotNull
    @DecimalMin("0.01")
    @Digits(integer = 16, fraction = 2)
    BigDecimal amount,

    @NotNull
    @Positive
    Integer termMonths,

    @Size(max = 200)
    String purpose,

    @DecimalMin("0.00")
    @Digits(integer = 3, fraction = 2)
    BigDecimal annualInterestRate
) {
}
