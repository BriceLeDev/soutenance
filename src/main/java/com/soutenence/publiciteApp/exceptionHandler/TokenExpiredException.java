package com.soutenence.publiciteApp.exceptionHandler;

public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException(String msg) {
        super(msg);
    }
}
