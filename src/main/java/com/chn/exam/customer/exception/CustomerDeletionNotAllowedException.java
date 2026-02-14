package com.chn.exam.customer.exception;

import java.util.UUID;

import com.chn.exam.common.exception.BusinessException;

public class CustomerDeletionNotAllowedException extends BusinessException {
    private CustomerDeletionNotAllowedException(String message) {
        super(message);
    }

    public static CustomerDeletionNotAllowedException forPendingLoans(UUID customerId) {
        return new CustomerDeletionNotAllowedException(
                "No se puede eliminar el cliente porque tiene préstamos aprobados sin pagar."
        );
    }
}
