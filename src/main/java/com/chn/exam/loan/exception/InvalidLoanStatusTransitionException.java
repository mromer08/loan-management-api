package com.chn.exam.loan.exception;

import com.chn.exam.common.exception.BusinessException;
import com.chn.exam.loan.model.LoanStatus;

public class InvalidLoanStatusTransitionException extends BusinessException {
    private InvalidLoanStatusTransitionException(String message) {
        super(message);
    }

    public static InvalidLoanStatusTransitionException forAction(String action, LoanStatus currentStatus) {
        return new InvalidLoanStatusTransitionException(
                String.format(
                        "No se puede %s una solicitud de préstamo en estado '%s'. Solo se permite cuando está en estado 'IN_PROCESS'",
                        action,
                        currentStatus));
    }
}
