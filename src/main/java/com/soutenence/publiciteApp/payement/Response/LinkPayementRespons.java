package com.soutenence.publiciteApp.payement.Response;

import lombok.Builder;

@Builder
public record LinkPayementRespons(
         String code,
         String message,
         String description,
         Data data,
         String api_response_id) {

    public static record Data(
            String payment_token,
            String payment_url
    ) {}

    // Getters et Setters

}
