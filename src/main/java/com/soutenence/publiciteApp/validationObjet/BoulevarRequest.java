package com.soutenence.publiciteApp.validationObjet;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record BoulevarRequest(


        @NotEmpty(message = "Veuillez renseigner le nom du boulevard")
        @NotNull(message = "Veuillez renseigner le nom du boulevard")
        String name

) {
}
