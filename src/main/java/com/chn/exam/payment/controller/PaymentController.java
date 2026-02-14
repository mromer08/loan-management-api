package com.chn.exam.payment.controller;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chn.exam.common.dto.PagedResponseDTO;
import com.chn.exam.payment.dto.LoanPaymentResponseDTO;
import com.chn.exam.payment.dto.RegisterLoanPaymentRequestDTO;
import com.chn.exam.payment.service.PaymentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/loans")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/{loanId}/payments")
    public ResponseEntity<PagedResponseDTO<LoanPaymentResponseDTO>> getLoanPayments(
            @PathVariable UUID loanId,
            @PageableDefault Pageable pageable) {
        PagedResponseDTO<LoanPaymentResponseDTO> payments = paymentService.getLoanPayments(
                loanId,
                pageable);
        return ResponseEntity.ok(payments);
    }

    @PostMapping("{loanId}/payments")
    public ResponseEntity<LoanPaymentResponseDTO> registerLoanPayment(
            @PathVariable UUID loanId,
            @RequestBody @Valid RegisterLoanPaymentRequestDTO requestDTO) {
        LoanPaymentResponseDTO payment = paymentService.registerLoanPayment(loanId, requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(payment);
    }
}
