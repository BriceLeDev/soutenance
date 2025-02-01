package com.soutenence.publiciteApp.ResponseAndRequest;

import com.soutenence.publiciteApp.entity.Panneau;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LigneAbonnementResponse {

    private Long panneauId;
    private  String boulevardName;
    private  String emplacement;
    private int nbrFace;
    private Long price;
    private Long printPrice;

}
