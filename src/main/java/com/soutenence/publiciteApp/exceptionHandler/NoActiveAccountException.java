package com.soutenence.publiciteApp.exceptionHandler;

public class NoActiveAccountException extends RuntimeException {
    public NoActiveAccountException(String message) {
     super(message);
    }
}
