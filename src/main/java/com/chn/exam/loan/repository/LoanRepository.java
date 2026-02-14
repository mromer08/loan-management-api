package com.chn.exam.loan.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.chn.exam.loan.model.Loan;

public interface LoanRepository extends
                JpaRepository<Loan, UUID>,
                JpaSpecificationExecutor<Loan> {
        Page<Loan> findByCustomerId(UUID customerId, Specification<Loan> specification, Pageable pageable);
}
