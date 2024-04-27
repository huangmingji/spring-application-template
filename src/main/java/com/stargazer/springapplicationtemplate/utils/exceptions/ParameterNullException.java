
package com.stargazer.springapplicationtemplate.utils.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParameterNullException extends RuntimeException {
    private static final Logger log = LoggerFactory.getLogger(VerifyPasswordException.class); 
    public ParameterNullException(String message) {
        super(message);
        log.error(message, getCause());
    }
}

