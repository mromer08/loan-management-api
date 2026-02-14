package com.chn.exam.common.exception;

public class DuplicateEntityException extends BusinessException {
    public DuplicateEntityException(String message) {
        super(message);
    }

    public DuplicateEntityException(){
        this("Duplicate entity found");
    }
}
