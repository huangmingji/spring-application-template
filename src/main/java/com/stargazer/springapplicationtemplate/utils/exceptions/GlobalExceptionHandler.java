package com.stargazer.springapplicationtemplate.utils.exceptions;

import com.stargazer.springapplicationtemplate.utils.I18nUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(DataNotExistsException.class)
    public ResponseEntity<ExceptionDetail> handleDataNotExistsException(DataNotExistsException ex) {
        log.warn(ex.getMessage());
        ExceptionDetail detail = new ExceptionDetail();
        detail.setCode(HttpStatus.NOT_FOUND.value());
        detail.setMessage(I18nUtils.get("data.not.exists", ex.getMessage()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(detail);
    }

    @ExceptionHandler(DataAlreadyExistsException.class)
    public ResponseEntity<ExceptionDetail> handleDataAlreadyExistsException(DataAlreadyExistsException ex) {
        log.warn(ex.getMessage());
        ExceptionDetail detail = new ExceptionDetail();
        detail.setCode(HttpStatus.CONFLICT.value());
        detail.setMessage(I18nUtils.get("data.already.exists", ex.getMessage()));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(detail);
    }

    @ExceptionHandler(VerifyPasswordException.class)
    public ResponseEntity<ExceptionDetail> handleVerifyPasswordException(VerifyPasswordException ex) {
        log.warn(ex.getMessage());
        ExceptionDetail detail = new ExceptionDetail();
        detail.setCode(HttpStatus.UNAUTHORIZED.value());
        detail.setMessage(I18nUtils.get("password.error", ex.getMessage()));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(detail);
    }

    @ExceptionHandler(ParameterEmptyException.class)
    public ResponseEntity<ExceptionDetail> handleParameterEmptyException(ParameterEmptyException ex) {
        log.warn(ex.getMessage());
        ExceptionDetail detail = new ExceptionDetail();
        detail.setCode(HttpStatus.BAD_REQUEST.value());
        detail.setMessage(I18nUtils.get("parameter.empty", ex.getMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(detail);
    }

    @ExceptionHandler(ParameterNullException.class)
    public ResponseEntity<ExceptionDetail> handleParameterNullException(ParameterNullException ex) {
        log.warn(ex.getMessage());
        ExceptionDetail detail = new ExceptionDetail();
        detail.setCode(HttpStatus.BAD_REQUEST.value());
        detail.setMessage(I18nUtils.get("parameter.null", ex.getMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(detail);
    }

    @ExceptionHandler(ParameterInvalidException.class)
    public ResponseEntity<ExceptionDetail> handleParameterInvalidException(ParameterInvalidException ex) {
        log.warn(ex.getMessage());
        ExceptionDetail detail = new ExceptionDetail();
        detail.setCode(HttpStatus.BAD_REQUEST.value());
        detail.setMessage(I18nUtils.get("invalid.parameter", ex.getMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(detail);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDetail> handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        ExceptionDetail detail = new ExceptionDetail();
        detail.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        detail.setMessage(I18nUtils.get("server.error"));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(detail);
    }
}