package com.project.finances.domain.exception;


public class BadRequestException extends RuntimeException {
    public BadRequestException(String message){
        super(message);
    }
}
