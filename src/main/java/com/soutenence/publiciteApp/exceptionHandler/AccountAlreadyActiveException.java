package com.soutenence.publiciteApp.exceptionHandler;

public class AccountAlreadyActiveException extends RuntimeException {
    public AccountAlreadyActiveException(String s) {
        super(s);
    }
}
