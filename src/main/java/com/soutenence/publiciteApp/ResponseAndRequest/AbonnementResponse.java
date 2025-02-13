package com.soutenence.publiciteApp.ResponseAndRequest;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

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
    private long nbrJrs;
    private boolean actif;
    private boolean valid;
    private String description;
    private String user;
    private List<byte[]> picture;
}
