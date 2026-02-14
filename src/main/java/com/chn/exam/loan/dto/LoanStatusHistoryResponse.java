package com.chn.exam.loan.dto;

import java.time.Instant;
import java.util.UUID;

import com.chn.exam.loan.model.LoanStatus;

public record LoanStatusHistoryResponse(
    UUID id,
    UUID loanId,
    LoanStatus status,
    String notes,
    Instant createdAt,
    Instant updatedAt
) {}
