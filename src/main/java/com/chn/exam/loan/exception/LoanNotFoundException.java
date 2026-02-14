package com.chn.exam.loan.exception;

import java.util.UUID;

import com.chn.exam.common.exception.EntityNotFoundException;

public class LoanNotFoundException extends EntityNotFoundException {
    private LoanNotFoundException(String message) {
        super(message);
    }

    public static LoanNotFoundException forId(UUID id) {
        return new LoanNotFoundException(
                String.format("No se encontró una solicitud de préstamo con el ID '%s'", id));
    }
}
