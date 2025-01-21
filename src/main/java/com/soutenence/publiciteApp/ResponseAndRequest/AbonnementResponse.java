package com.soutenence.publiciteApp.ResponseAndRequest;

import com.soutenence.publiciteApp.entity.LigneAbonnement;
import com.soutenence.publiciteApp.entity.User;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AbonnementResponse {

    private double prix;
    private double mtnPayer;
    private double mtnRest;
    private int duree;
    private LocalDate dateFin;
    private LocalDate dateDebut;
    LocalDate dateAbn;
    private int nbrJrs;
    private boolean actif;
    private String description;



}
