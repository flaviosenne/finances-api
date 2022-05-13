package com.project.finances.app.rest.exception;

import com.project.finances.domain.exception.BadRequestException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CustomException> handleBadRequestException(BadRequestException badRequestException, HttpServletRequest request){
        return new ResponseEntity<>(
                CustomException.builder()
                        .path(request.getRequestURI())
                        .message(badRequestException.getMessage())
                        .title("Bad Request Exception, Check the Documentation")
                        .timestamp(new Date().getTime())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .build(), HttpStatus.BAD_REQUEST
        );
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<CustomException> handleBadCredentialRequestException(BadCredentialsException badRequestException, HttpServletRequest request){
        return new ResponseEntity<>(
                CustomException.builder()
                        .path(request.getRequestURI())
                        .message(badRequestException.getMessage())
                        .title("Bad Credentials Exception, Check the Documentation")
                        .timestamp(new Date().getTime())
                        .status(HttpStatus.FORBIDDEN.value())
                        .build(), HttpStatus.FORBIDDEN
        );
    }
     @org.springframework.web.bind.annotation.ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity<CustomException> handleBadCredentialRequestException(DataIntegrityViolationException dataIntegrityViolationException, HttpServletRequest request){
            return new ResponseEntity<>(
                    CustomException.builder()
                            .path(request.getRequestURI())
                            .message(dataIntegrityViolationException.getMessage())
                            .title("Bad Request Exception, Check the Documentation")
                            .timestamp(new Date().getTime())
                            .status(HttpStatus.BAD_REQUEST.value())
                            .build(), HttpStatus.BAD_REQUEST
            );
        }

}
