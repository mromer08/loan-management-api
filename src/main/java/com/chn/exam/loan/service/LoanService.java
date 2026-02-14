package com.chn.exam.loan.service;

import java.util.UUID;

import org.springframework.data.domain.Pageable;

import com.chn.exam.common.dto.PagedResponseDTO;
import com.chn.exam.loan.dto.GetLoansRequestDTO;
import com.chn.exam.loan.dto.LoanResponseDTO;
import com.chn.exam.loan.dto.LoanStatusHistoryResponse;
import com.chn.exam.loan.dto.ReviewLoanApplicationRequestDTO;
import com.chn.exam.loan.dto.SubmitLoanApplicationRequestDTO;

public interface LoanService {
    LoanResponseDTO getLoanById(UUID loanId);
    LoanResponseDTO submitLoanApplication(UUID customerId, SubmitLoanApplicationRequestDTO request);
    PagedResponseDTO<LoanResponseDTO> getLoanApplicationsByCustomer(UUID customerId, GetLoansRequestDTO request, Pageable pageable);
    LoanResponseDTO approveLoanApplication(UUID loanId, ReviewLoanApplicationRequestDTO request);
    LoanResponseDTO rejectLoanApplication(UUID loanId, ReviewLoanApplicationRequestDTO request);
    PagedResponseDTO<LoanStatusHistoryResponse> getLoanStatusHistory(UUID loanId, Pageable pageable);
}
