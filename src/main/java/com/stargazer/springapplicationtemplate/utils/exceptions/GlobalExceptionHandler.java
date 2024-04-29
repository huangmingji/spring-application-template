package com.stargazer.springapplicationtemplate.utils.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class); 

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        try {
            ObjectMapper mapper = new ObjectMapper();
            ExceptionDetail detail = new ExceptionDetail();
            detail.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            detail.setMessage(ex.getMessage());
            return new ResponseEntity<>(mapper.writeValueAsString(detail), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(DataAlreadyExistsException.class)
    public ResponseEntity<String> handleDataAlreadyExistsException(DataAlreadyExistsException ex) {
        log.error(ex.getMessage(), ex);
        try {
            ObjectMapper mapper = new ObjectMapper();
            ExceptionDetail detail = new ExceptionDetail();
            detail.setCode(HttpStatus.BAD_REQUEST.value());
            detail.setMessage(ex.getMessage());
            // 返回400 Bad Request状态码及错误信息
            return new ResponseEntity<>(mapper.writeValueAsString(detail), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(DataNotExistsException.class)
    public ResponseEntity<String> handleDataNotExistsException(DataNotExistsException ex) {
        log.error(ex.getMessage(), ex);
        try {
            ObjectMapper mapper = new ObjectMapper();
            ExceptionDetail detail = new ExceptionDetail();
            detail.setCode(HttpStatus.BAD_REQUEST.value());
            detail.setMessage(ex.getMessage());
            // 返回400 Bad Request状态码及错误信息
            return new ResponseEntity<>(mapper.writeValueAsString(detail), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(VerifyPasswordException.class)
    public ResponseEntity<String> handleVerifyPasswordException(VerifyPasswordException ex) {
        log.error(ex.getMessage(), ex);
        try {
            ObjectMapper mapper = new ObjectMapper();
            ExceptionDetail detail = new ExceptionDetail();
            detail.setCode(HttpStatus.BAD_REQUEST.value());
            detail.setMessage(ex.getMessage());
            // 返回400 Bad Request状态码及错误信息
            return new ResponseEntity<>(mapper.writeValueAsString(detail), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(ParameterEmptyException.class)
    public ResponseEntity<String> handleParameterEmptyException(ParameterEmptyException ex) {
        log.error(ex.getMessage(), ex);
        try {
            ObjectMapper mapper = new ObjectMapper();
            ExceptionDetail detail = new ExceptionDetail();
            detail.setCode(HttpStatus.BAD_REQUEST.value());
            detail.setMessage(ex.getMessage());
            // 返回400 Bad Request状态码及错误信息
            return new ResponseEntity<>(mapper.writeValueAsString(detail), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(ParameterNullException.class)
    public ResponseEntity<String> handleParameterNullException(ParameterNullException ex) {
        log.error(ex.getMessage(), ex);
        try {
            ObjectMapper mapper = new ObjectMapper();
            ExceptionDetail detail = new ExceptionDetail();
            detail.setCode(HttpStatus.BAD_REQUEST.value());
            detail.setMessage(ex.getMessage());
            // 返回400 Bad Request状态码及错误信息
            return new ResponseEntity<>(mapper.writeValueAsString(detail), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(ParameterInvalidException.class)
    public ResponseEntity<String> handleParameterInvalidException(ParameterInvalidException ex) {
        log.error(ex.getMessage(), ex);
        try {
            ObjectMapper mapper = new ObjectMapper();
            ExceptionDetail detail = new ExceptionDetail();
            detail.setCode(HttpStatus.BAD_REQUEST.value());
            detail.setMessage(ex.getMessage());
            // 返回400 Bad Request状态码及错误信息
            return new ResponseEntity<>(mapper.writeValueAsString(detail), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
