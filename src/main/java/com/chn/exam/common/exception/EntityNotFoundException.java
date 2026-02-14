package com.chn.exam.common.exception;

public class EntityNotFoundException extends BusinessException {
    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(){
        this("Entity not found");
    }
    
}
