package com.soutenence.publiciteApp.exceptionHandler;

public class TokenNonValideException extends RuntimeException {
    public TokenNonValideException(String msg) {

        super(msg);
    }
}
