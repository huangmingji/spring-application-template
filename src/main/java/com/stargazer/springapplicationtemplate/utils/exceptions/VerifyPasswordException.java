package com.stargazer.springapplicationtemplate.utils.exceptions;

public class VerifyPasswordException extends RuntimeException {

    public VerifyPasswordException(String account) {
        super("账户密码错误");
    }

    public VerifyPasswordException(Long id) {
        super("账户密码错误");
    }
}
