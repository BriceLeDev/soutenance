package com.soutenence.publiciteApp.exceptionHandler;

public class AccountStatusActiveException extends RuntimeException {
    public AccountStatusActiveException(String s) {
        super(s);
    }
}
