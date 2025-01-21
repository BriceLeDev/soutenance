package com.soutenence.publiciteApp.ResponseAndRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record TypePanRequest(
            @NotBlank(message = "Saisissez le type du panneau")
            @NotEmpty(message = "Saisissez le type du panneau")
            String libellet
)
{
}
