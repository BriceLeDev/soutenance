package com.soutenence.publiciteApp.ResponseAndRequest;

import jakarta.validation.constraints.*;

public record PanneauRquest(
        @NotEmpty(message = "Veuillez renseigner la localisation")
        @NotBlank(message = "Veuillez renseigner la localisation")
        String localisation,
        @Positive(message = "Veuillez renseigner une taille")
        Integer taille,
        @Positive(message = "Veuillez choisir un boulevard")
        @Min(value = 1)
        Integer boulevard_id,
        @NotEmpty(message = "Veuillez choisir le type panneau")
        @NotBlank(message = "Veuillez choisir le type panneau")
        String typePanneau,
        @Positive(message = "Veuillez renseigner un prix journalier")
        Long prixMensuel,
        @Positive(message = "Veuillez renseigner un prix d'impression affiche du panneau")
        Long printPrice,
        @Positive(message = "Veuillez renseigner le nombre de face du panneau")
        int nbRFace
) {
}


