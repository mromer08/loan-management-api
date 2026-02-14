package com.chn.exam.loan.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ReviewLoanApplicationRequestDTO(
    @NotBlank
    @Size(max = 200)
    String notes
) {
}
