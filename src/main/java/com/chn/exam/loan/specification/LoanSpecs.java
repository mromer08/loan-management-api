package com.chn.exam.loan.specification;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import com.chn.exam.loan.model.Loan;
import com.chn.exam.loan.model.LoanStatus;

import lombok.experimental.UtilityClass;

@UtilityClass
public class LoanSpecs {

    public Specification<Loan> statusIn(Set<LoanStatus> statuses) {
        return (root, query, criteriaBuilder) -> (statuses == null || statuses.isEmpty())
                ? null
                : root.get("status").in(statuses);
    }

    public Specification<Loan> statusEquals(LoanStatus status) {
        return (root, query, criteriaBuilder) -> (status == null)
                ? null
                : criteriaBuilder.equal(root.get("status"), status);
    }

    public Specification<Loan> customerIdEquals(UUID customerId) {
        return (root, query, criteriaBuilder) -> (customerId == null)
                ? null
                : criteriaBuilder.equal(root.get("customer").get("id"), customerId);
    }
}
