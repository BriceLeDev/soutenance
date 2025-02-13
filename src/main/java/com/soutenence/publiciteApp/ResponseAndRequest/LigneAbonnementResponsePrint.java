package com.soutenence.publiciteApp.ResponseAndRequest;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LigneAbonnementResponsePrint {

    private Long panneauId;
    private  String boulevardName;
    private  String emplacement;
    private int nbrFace;
    private  int taille;
    private  String type;

}
