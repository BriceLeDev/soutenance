package com.soutenence.publiciteApp.ResponseAndRequest;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.util.List;

public record AbonnementRequest(

        @Positive(message = "Le prix doit être supérieur à 0")
        @NotNull(message = "Le prix doit être définir")
        double prix,

        @NotNull(message = "La date début doit être définir")
        LocalDate dateDebut,

        @NotNull(message = "La date du jour doit être définir")
        LocalDate dateAbn,


        @NotNull(message = "La date fin doit être définir")
        LocalDate dateFin,

        @NotEmpty(message = "la description doit être définir")
        @NotNull(message = "la description doit être définir")
        String description,

        List<Integer> Panneau

) {
}
