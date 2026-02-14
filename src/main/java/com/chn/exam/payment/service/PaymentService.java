package com.chn.exam.payment.service;

import java.util.UUID;

import org.springframework.data.domain.Pageable;

import com.chn.exam.common.dto.PagedResponseDTO;
import com.chn.exam.payment.dto.LoanPaymentResponseDTO;
import com.chn.exam.payment.dto.RegisterLoanPaymentRequestDTO;

public interface PaymentService {
    PagedResponseDTO<LoanPaymentResponseDTO> getLoanPayments(UUID loanId, Pageable pageable);

    LoanPaymentResponseDTO registerLoanPayment(UUID loanId, RegisterLoanPaymentRequestDTO request);
}
