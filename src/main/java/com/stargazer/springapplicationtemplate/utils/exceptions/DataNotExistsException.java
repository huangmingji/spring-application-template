package com.stargazer.springapplicationtemplate.utils.exceptions;

public class DataNotExistsException extends RuntimeException {
    public DataNotExistsException(String message) {
        super(message);
    }
}
