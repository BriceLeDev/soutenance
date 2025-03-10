package com.soutenence.publiciteApp.exceptionHandler;

import org.springframework.http.HttpStatus;

public enum BusinessErrorCode {

    NO_CODE(0,HttpStatus.NOT_IMPLEMENTED,"No code"),
    USER_ALREADY_EXISTS(409, HttpStatus.CONFLICT, "Cet email existe déjà."),
    INCORRECT_PASSWORD(300,HttpStatus.BAD_REQUEST,"Les mots de passe sont incorrects"),
    BAD_CREDENTIAL(301,HttpStatus.BAD_REQUEST,"Mot de passe ou nom utilisateur incorrect"),
    ACCOUNT_LOCKED(302,HttpStatus.NOT_FOUND,"Se compte à été blocké"),
    UNACTIVE_ACCOUNT(303,HttpStatus.NOT_ACCEPTABLE,"Compte non actif"),
    ACTIVE_ACCOUNT(304,HttpStatus.NOT_ACCEPTABLE,"Activation"),
    INVALID_TOKEN(1000,HttpStatus.NOT_ACCEPTABLE,"Invalide Token"),
    BLOCKED_ACCOUNT(305,HttpStatus.NOT_ACCEPTABLE,"Se compte à été blocké"),
    FIDEL_ACCOUNT(306,HttpStatus.NOT_ACCEPTABLE,"Fidélisation"),
    TOKEN_EXPIRED(1001,HttpStatus.NOT_ACCEPTABLE, "Token expired");
    ;

    private final int code;
    private  final String description;
    private  final HttpStatus status;

    BusinessErrorCode(int code, HttpStatus status, String description) {
        this.code = code;
        this.description = description;
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
