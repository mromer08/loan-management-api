package com.chn.exam.customer.exception;

import java.util.UUID;

import com.chn.exam.common.exception.EntityNotFoundException;

public class CustomerNotFoundException extends EntityNotFoundException{
    private CustomerNotFoundException(String message) {
        super(message);
    }
    public static CustomerNotFoundException forIdentificationNumber(String identificationNumber) {
        return new CustomerNotFoundException(
            String.format("No se encontró un cliente con el número de identificación '%s'", identificationNumber)
        );
    }
    public static CustomerNotFoundException forId(UUID id) {
        return new CustomerNotFoundException(
            String.format("No se encontró un cliente con el ID '%s'", id)
        );
    }
}
