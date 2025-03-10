package com.soutenence.publiciteApp.exceptionHandler;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;

@RestControllerAdvice
@ControllerAdvice
public class GlobalExceptionHandle {

    /*
    * Pour handle les exceptions en java il faut :
    *       - créer une classe Globale avec les différentes exceptions
    *       - créer une classe responseException pour...
    *       - créer une Enum pour définir ou énumerer les types d'exception
    * */
    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionResponse> ExceptionHandler(LockedException ex){
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(
                            ExceptionResponse.builder()
                                    .busynesErrorCode(BusinessErrorCode.ACCOUNT_LOCKED.getCode())
                                    .bussnessErrorDescription(BusinessErrorCode.ACCOUNT_LOCKED.getDescription())
                                    .error(ex.getMessage())
                                    .build()
                    );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> ExceptionHandler(BadCredentialsException ex){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                               .error(ex.getMessage())
                                .build()
                );
    }
    @ExceptionHandler(IncorresctPasseWord.class)
    public ResponseEntity<ExceptionResponse> ExceptionHandler(IncorresctPasseWord ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .busynesErrorCode(BusinessErrorCode.INCORRECT_PASSWORD.getCode())
                                .bussnessErrorDescription(BusinessErrorCode.INCORRECT_PASSWORD.getDescription())
                                .error(ex.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(NoActiveAccountException.class)
    public ResponseEntity<ExceptionResponse> ExceptionHandler(NoActiveAccountException ex){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .busynesErrorCode(BusinessErrorCode.UNACTIVE_ACCOUNT.getCode())
                                .bussnessErrorDescription(BusinessErrorCode.UNACTIVE_ACCOUNT.getDescription())
                                .error(ex.getMessage())
                                .build()
                );
    }
    @ExceptionHandler(AccountStatusActiveException.class)
    public ResponseEntity<ExceptionResponse> ExceptionHandler(AccountStatusActiveException ex){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .busynesErrorCode(BusinessErrorCode.UNACTIVE_ACCOUNT.getCode())
                                .bussnessErrorDescription(BusinessErrorCode.UNACTIVE_ACCOUNT.getDescription())
                                .error(ex.getMessage())
                                .build()
                );
    }
    @ExceptionHandler(AccountFidelityActiveException.class)
    public ResponseEntity<ExceptionResponse> ExceptionHandler(AccountFidelityActiveException ex){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .busynesErrorCode(BusinessErrorCode.UNACTIVE_ACCOUNT.getCode())
                                .bussnessErrorDescription(BusinessErrorCode.UNACTIVE_ACCOUNT.getDescription())
                                .error(ex.getMessage())
                                .build()
                );
    }
    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<ExceptionResponse> ExceptionHandler(UserAlreadyExists ex){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        ExceptionResponse.builder()
                                .busynesErrorCode(BusinessErrorCode.USER_ALREADY_EXISTS.getCode())
                                .bussnessErrorDescription(BusinessErrorCode.USER_ALREADY_EXISTS.getDescription())
                                .error(ex.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(TokenNonValideException.class)
    public ResponseEntity<ExceptionResponse> ExceptionHandler(TokenNonValideException ex){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .busynesErrorCode(BusinessErrorCode.INVALID_TOKEN.getCode())
                                .bussnessErrorDescription(BusinessErrorCode.INVALID_TOKEN.getDescription())
                                .error(ex.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(AccountAlreadyActiveException.class)
    public ResponseEntity<ExceptionResponse> ExceptionHandler(AccountAlreadyActiveException ex){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .busynesErrorCode(BusinessErrorCode.ACTIVE_ACCOUNT.getCode())
                                .bussnessErrorDescription(BusinessErrorCode.ACTIVE_ACCOUNT.getDescription())
                                .error(ex.getMessage())
                                .build()
                );
    }
    //exception pour handle les erreur de validation du formulaire d'inscription
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> ExceptionHandler(MethodArgumentNotValidException ex){

        Set<String> errors = new HashSet<>();
        ex.getBindingResult().getAllErrors()
                .forEach(error->{
                    var errorMessage = error.getDefaultMessage();
                    errors.add(errorMessage);
                });
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .validationError(errors)
                                .build()
                );
    }
    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ExceptionResponse> ExceptionHandler(TokenExpiredException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .busynesErrorCode(BusinessErrorCode.TOKEN_EXPIRED.getCode())
                                .bussnessErrorDescription(BusinessErrorCode.TOKEN_EXPIRED.getDescription())
                                .error(ex.getMessage())
                                .build()
                );
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> ExceptionHandler(EntityNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .error(ex.getMessage())
                                .build()
                );
    }

    //exception pour handle tout type d exception non spécifier dans l application
 /* @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> ExceptionHandler(Exception ex){
        //nécessite d etre enrégistrer dans le fichier log
        ex.getStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder()
                                .bussnessErrorDescription("Une erreur s' est produites veuillez contactez l administrateur")
                                .error(ex.getMessage())
                                .build()
                );
    }*/
}
