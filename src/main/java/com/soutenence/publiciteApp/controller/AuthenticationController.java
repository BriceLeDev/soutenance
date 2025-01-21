package com.soutenence.publiciteApp.controller;

import com.soutenence.publiciteApp.service.AuthenticationService;
import com.soutenence.publiciteApp.validationObjet.LoginFormRequest;
import com.soutenence.publiciteApp.validationObjet.LoginFormResponse;
import com.soutenence.publiciteApp.validationObjet.RegistrationFormRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("auth")
@Tag(name = "Authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> register(@RequestBody @Valid RegistrationFormRequest request) throws MessagingException {
        authenticationService.register(request);
        return ResponseEntity.accepted().build();
    }
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<LoginFormResponse> login(@RequestBody @Valid LoginFormRequest request)
            throws MessagingException {
        return ResponseEntity.ok(authenticationService.login(request));
    }
    @GetMapping("/activate-account")
    public void confirmation(@RequestParam String token) throws MessagingException {
        authenticationService.activateAccount(token);
    }
    @PostMapping("/refresh-token")
    public void refreshToken(@NotNull HttpServletRequest request,
                             @NotNull HttpServletResponse response) throws IOException {

        authenticationService.refreshingToken(request,response);
    }


}


