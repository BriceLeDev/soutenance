package com.soutenence.publiciteApp.validationObjet;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class LoginFormRequest {
    @NotEmpty(message = "Veuillez saisir un email")
    @NotBlank(message = "Veuillez saisir un email")
    @Email(message = "Email invalide")
    private String email;
    @NotEmpty(message = "Veuillez saisir un mot de passe")
    @NotBlank(message = "Veuillez saisir un mot de passe")
    @Size(min = 4, message = "Le mot de passe doit dépassé 4 caractères")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
