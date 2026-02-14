package com.chn.exam.payment.exception;

import java.math.BigDecimal;

import com.chn.exam.common.exception.BusinessException;
import com.chn.exam.loan.model.LoanStatus;

public class InvalidLoanPaymentException extends BusinessException {
    private InvalidLoanPaymentException(String message) {
        super(message);
    }

    public static InvalidLoanPaymentException forLoanStatus(LoanStatus loanStatus) {
        return new InvalidLoanPaymentException(
                String.format("Solo se puede registrar pagos para préstamos en estado 'APPROVED'. Estado actual: '%s'",
                        loanStatus));
    }

    public static InvalidLoanPaymentException invalidOutstandingBalance() {
        return new InvalidLoanPaymentException(
                "El préstamo aprobado no tiene saldo pendiente válido para aplicar pagos");
    }

    public static InvalidLoanPaymentException amountExceedsOutstanding(BigDecimal amount, BigDecimal outstandingBalance) {
        return new InvalidLoanPaymentException(
                String.format("El monto del pago '%s' excede el saldo pendiente '%s'", amount, outstandingBalance));
    }
}
