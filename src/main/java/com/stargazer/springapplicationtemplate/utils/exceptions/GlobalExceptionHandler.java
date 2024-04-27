package com.stargazer.springapplicationtemplate.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DataAlreadyExistsException.class)
    public ResponseEntity<String> handleDataAlreadyExistsException(DataAlreadyExistsException ex) {
        // 返回400 Bad Request状态码及错误信息
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataNotExistsException.class)
    public ResponseEntity<String> handleDataNotExistsException(DataNotExistsException ex) {
        // 返回400 Bad Request状态码及错误信息
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(VerifyPasswordException.class)
    public ResponseEntity<String> handleVerifyPasswordException(VerifyPasswordException ex) {
        // 返回400 Bad Request状态码及错误信息
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(ParameterEmptyException.class)
    public ResponseEntity<String> handleParameterEmptyException(ParameterEmptyException ex) {
        // 返回400 Bad Request状态码及错误信息
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ParameterNullException.class)
    public ResponseEntity<String> handleParameterNullException(ParameterNullException ex) {
        // 返回400 Bad Request状态码及错误信息
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ParameterInvalidException.class)
    public ResponseEntity<String> handleParameterInvalidException(ParameterInvalidException ex) {
        // 返回400 Bad Request状态码及错误信息
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
