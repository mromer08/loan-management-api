package com.chn.exam.payment.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.chn.exam.loan.model.LoanStatus;
import com.chn.exam.payment.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    Page<Payment> findByLoanCustomerIdAndLoanStatus(UUID customerId, LoanStatus loanStatus, Pageable pageable);

    Page<Payment> findByLoanIdAndLoanCustomerIdAndLoanStatus(UUID loanId, UUID customerId, LoanStatus loanStatus,
            Pageable pageable);

    Page<Payment> findByLoanId(UUID id, Pageable pageable);
}
