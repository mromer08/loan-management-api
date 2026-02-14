package com.chn.exam.loan.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.chn.exam.loan.model.LoanStatusHistory;

public interface LoanStatusHistoryRepository extends JpaRepository<LoanStatusHistory, UUID> {
    Page<LoanStatusHistory> findByLoanId(UUID loanId, Pageable pageable);
}
