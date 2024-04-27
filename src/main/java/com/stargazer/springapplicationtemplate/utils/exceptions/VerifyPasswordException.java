package com.stargazer.springapplicationtemplate.utils.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VerifyPasswordException extends RuntimeException {

    private static final Logger log = LoggerFactory.getLogger(VerifyPasswordException.class); 

    public VerifyPasswordException(String account) {
        super("账户密码错误");
        log.error(account, getCause());
    }

    public VerifyPasswordException(Long id) {
        super("账户密码错误");
        log.error(id.toString(), getCause());
    }
}
