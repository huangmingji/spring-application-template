package com.stargazer.springapplicationtemplate.utils.exceptions;

public class DataAlreadyExistsException extends RuntimeException {
    public DataAlreadyExistsException(String message) {
        super(message);
    }
}
