package com.chn.exam.loan.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chn.exam.loan.model.Loan;
import com.chn.exam.loan.model.LoanPaymentStatus;
import com.chn.exam.loan.model.LoanStatus;

public interface LoanRepository extends
                JpaRepository<Loan, UUID>,
                JpaSpecificationExecutor<Loan> {
        Page<Loan> findByCustomerId(UUID customerId, Specification<Loan> specification, Pageable pageable);

        @Query("""
                        SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END
                        FROM Loan l
                        WHERE l.customer.id = :customerId
                          AND l.status = :status
                          AND l.paymentStatus <> :paidStatus
                        """)
        boolean hasPendingLoanByCustomerId(
                        @Param("customerId") UUID customerId,
                        @Param("status") LoanStatus status,
                        @Param("paidStatus") LoanPaymentStatus paidStatus);
}
