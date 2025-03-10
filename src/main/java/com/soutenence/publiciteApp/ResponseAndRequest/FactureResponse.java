package com.soutenence.publiciteApp.ResponseAndRequest;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FactureResponse {
    private int id;
    private String reference;
    private Double mtnTotal;
    private Integer mtnPayer;
    private Double mtnRestant;
    private LocalDate datePayment;
    private LocalDate dateDebAbn;
    private LocalDate dateFinAbn;
    private LocalDate dateAbn;
    private String transaction;
    private long abonnementId;
}
