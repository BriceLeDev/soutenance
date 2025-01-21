package com.soutenence.publiciteApp.ResponseAndRequest;

import com.soutenence.publiciteApp.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public class adminFormRequest {

    @NotEmpty(message = "Veuillez saisir un email")
    @NotBlank(message = "Veuillez saisir un email")
    @Email(message = "Email invalide")
    private String email;
    @NotEmpty(message = "Veuillez saisir le nom utilisateur ")
    @NotBlank(message = "Veuillez saisir le nom utilisateur ")
    private String nonUtilisateur;
    @NotEmpty(message = "Veuillez saisir le role de l' utilisateur")
    @NotBlank(message = "Veuillez saisir le role de l' utilisateur")
    private String numero;
    @NotEmpty(message = "Veuillez saisir le role de l' utilisateur")
    @NotBlank(message = "Veuillez saisir le role de l' utilisateur")
    private List<Role> userRole;
    @NotEmpty(message = "Veuillez saisir un mot de passe")
    @NotBlank(message = "Veuillez saisir un mot de passe")
    @Size(min = 4, message = "Le mot de passe doit dépasser 4 caractères")
    private String password;

    public @NotEmpty(message = "Veuillez saisir un email") @NotBlank(message = "Veuillez saisir un email") @Email(message = "Email invalide") String getEmail() {
        return email;
    }

    public @NotEmpty(message = "Veuillez saisir le nom utilisateur ") @NotBlank(message = "Veuillez saisir le nom utilisateur ") String getNonUtilisateur() {
        return nonUtilisateur;
    }

    public @NotEmpty(message = "Veuillez saisir le role de l' utilisateur") @NotBlank(message = "Veuillez saisir le role de l' utilisateur") List<Role> getUserRole() {
        return userRole;
    }

    public @NotEmpty(message = "Veuillez saisir un mot de passe") @NotBlank(message = "Veuillez saisir un mot de passe") @Size(min = 4, message = "Le mot de passe doit dépasser 4 caractères") String getPassword() {
        return password;
    }

    public @NotEmpty(message = "Veuillez saisir le role de l' utilisateur") @NotBlank(message = "Veuillez saisir le role de l' utilisateur") String getNumero() {
        return numero;
    }

    public void setNumero(@NotEmpty(message = "Veuillez saisir le role de l' utilisateur") @NotBlank(message = "Veuillez saisir le role de l' utilisateur") String numero) {
        this.numero = numero;
    }
}
