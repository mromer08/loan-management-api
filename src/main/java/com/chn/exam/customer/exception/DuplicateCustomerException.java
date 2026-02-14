package com.chn.exam.customer.exception;

import com.chn.exam.common.exception.DuplicateEntityException;

public class DuplicateCustomerException extends DuplicateEntityException {
    private DuplicateCustomerException(String message) {
        super(message);
    }

    public static DuplicateCustomerException forIdentificationNumber(String identificationNumber) {
        return new DuplicateCustomerException(
            String.format("Ya existe un cliente con el número de identificación '%s'", identificationNumber)
        );
    }

    public static DuplicateCustomerException forPhone(String phone) {
        return new DuplicateCustomerException(
            String.format("Ya existe un cliente con el número de teléfono '%s'", phone)
        );
    }

    public static DuplicateCustomerException forEmail(String email) {
        return new DuplicateCustomerException(
            String.format("Ya existe un cliente con el correo electrónico '%s'", email)
        );
    }
    
}
