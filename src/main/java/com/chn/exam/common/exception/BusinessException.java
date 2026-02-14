package com.chn.exam.common.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(){
        this("Business exception occurred");
    }
}
