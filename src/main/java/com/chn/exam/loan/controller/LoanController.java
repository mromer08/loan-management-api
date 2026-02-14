package com.chn.exam.loan.controller;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.chn.exam.common.dto.PagedResponseDTO;
import com.chn.exam.loan.dto.*;
import com.chn.exam.loan.model.LoanStatus;
import com.chn.exam.loan.service.LoanService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LoanController {
    private final LoanService loanService;

    @GetMapping("/loans/{loanId}")
    public ResponseEntity<LoanResponseDTO> getLoanById(@PathVariable UUID loanId) {
        LoanResponseDTO loan = loanService.getLoanById(loanId);
        return ResponseEntity.ok(loan);
    }

    @GetMapping("/customers/{customerId}/loans")
    public ResponseEntity<PagedResponseDTO<LoanResponseDTO>> getLoanApplicationsByCustomer(
            @PathVariable UUID customerId,
            @RequestParam(required = false) LoanStatus status,
            @PageableDefault Pageable pageable) {
        GetLoansRequestDTO requestDTO = new GetLoansRequestDTO(status);
        PagedResponseDTO<LoanResponseDTO> loans = loanService.getLoanApplicationsByCustomer(customerId, requestDTO,
                pageable);
        return ResponseEntity.ok(loans);
    }

    @GetMapping("/loans/{loanId}/history")
    public ResponseEntity<PagedResponseDTO<LoanStatusHistoryResponse>> getLoanStatusHistory(
            @PathVariable UUID loanId,
            @PageableDefault Pageable pageable) {
        PagedResponseDTO<LoanStatusHistoryResponse> history = loanService.getLoanStatusHistory(loanId, pageable);
        return ResponseEntity.ok(history);
    }

    @PostMapping("/customers/{customerId}/loans")
    public ResponseEntity<LoanResponseDTO> submitLoanApplication(
            @PathVariable UUID customerId,
            @RequestBody @Valid SubmitLoanApplicationRequestDTO requestDTO) {
        LoanResponseDTO loan = loanService.submitLoanApplication(customerId, requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(loan);
    }

    @PostMapping("/loans/{loanId}/approve")
    public ResponseEntity<LoanResponseDTO> approveLoanApplication(
            @PathVariable UUID loanId,
            @RequestBody @Valid ReviewLoanApplicationRequestDTO requestDTO) {
        LoanResponseDTO loan = loanService.approveLoanApplication(loanId, requestDTO);
        return ResponseEntity.ok(loan);
    }

    @PostMapping("/loans/{loanId}/reject")
    public ResponseEntity<LoanResponseDTO> rejectLoanApplication(
            @PathVariable UUID loanId,
            @RequestBody @Valid ReviewLoanApplicationRequestDTO requestDTO) {
        LoanResponseDTO loan = loanService.rejectLoanApplication(loanId, requestDTO);
        return ResponseEntity.ok(loan);
    }
}
