package com.soutenence.publiciteApp.ResponseAndRequest;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AbonnementResponse {

    private Long id;
    private double prix;
    private double mtnPayer;
    private double mtnRest;
    private int duree;
    private LocalDate dateFin;
    private LocalDate dateDebut;
    private LocalDate dateAbn;
    private int nbrJrs;
    private boolean actif;
    private boolean valid;
    private String description;



}
