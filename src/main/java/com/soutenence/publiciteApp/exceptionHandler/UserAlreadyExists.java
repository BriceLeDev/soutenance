package com.soutenence.publiciteApp.exceptionHandler;

public class UserAlreadyExists extends RuntimeException {
    public UserAlreadyExists(String s) {
        super(s);
    }
}
