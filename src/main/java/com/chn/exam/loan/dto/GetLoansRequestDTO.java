package com.chn.exam.loan.dto;

import com.chn.exam.loan.model.LoanStatus;

public record GetLoansRequestDTO(
    // Set<LoanStatus> status
    LoanStatus status
) {}
