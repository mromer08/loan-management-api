package com.chn.exam.loan.dto;

import java.util.Set;

import com.chn.exam.loan.model.LoanStatus;

public record GetLoansRequestDTO(
    Set<LoanStatus> status
) {}
