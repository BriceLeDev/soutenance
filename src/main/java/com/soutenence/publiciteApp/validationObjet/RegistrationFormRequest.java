package com.soutenence.publiciteApp.validationObjet;

import jakarta.validation.constraints.*;

public class RegistrationFormRequest {

    @NotEmpty(message = "Veuillez saisir un email")
    @NotBlank(message = "Veuillez saisir un email")
    @Email(message = "Email invalide")
    private String email;
    @NotEmpty(message = "Veuillez saisir un utilisateur ou nom de l'entreprise")
    @NotBlank(message = "Veuillez saisir un nom utilisateur ou nom de l'entreprise")
    private String nonUtilisateur;
    @NotEmpty(message = "Veuillez saisir le numéro de téléphone")
    @NotBlank(message = "Veuillez saisir le numéro de téléphone")
    private String numero;
    @NotEmpty(message = "Veuillez saisir un mot de passe")
    @NotBlank(message = "Veuillez saisir un mot de passe")
    @Size(min = 4, message = "Le mot de passe doit dépasser 4 caractères")
    private String password;
    @NotEmpty(message = "Veuillez confirmer le mot de passe")
    @NotBlank(message = "Veuillez confirmer le mot de passe")
    private String confirmPassword;


    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNonUtilisateur() {
        return nonUtilisateur;
    }

    public void setNonUtilisateur(String nonUtilisateur) {
        this.nonUtilisateur = nonUtilisateur;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public @NotEmpty(message = "Veuillez saisir le numéro de téléphone") @NotBlank(message = "Veuillez saisir le numéro de téléphone") String getNumero() {
        return numero;
    }

    public void setNumero(@NotEmpty(message = "Veuillez saisir le numéro de téléphone") @NotBlank(message = "Veuillez saisir le numéro de téléphone") String numero) {
        this.numero = numero;
    }
}
