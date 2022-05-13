package com.project.finances.domain.exception;


public class ServerErrorException extends RuntimeException {
    public ServerErrorException(String message){
        super(message);
    }
}
