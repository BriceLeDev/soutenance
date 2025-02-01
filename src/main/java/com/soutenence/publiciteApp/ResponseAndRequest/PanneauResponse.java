package com.soutenence.publiciteApp.ResponseAndRequest;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PanneauResponse {
    private Long id;
    private String localisation;
    private Integer taille;
    private Long prixMensuel;
    private Long printPrice;
    private int nbrFace;
    private String boulevardName;
    private String typePanneauLibele;
    @Builder.Default
    boolean occuped=false;

   /* public PanneauResponse() {
    }

    public PanneauResponse(Integer id, String localisation, Integer taille, Long prixMensuel, Long printPrice, Boulevard boulevard, TypePanneau typePanneau, boolean occuped) {
        this.id = id;
        this.localisation = localisation;
        this.taille = taille;
        this.prixMensuel = prixMensuel;
        this.printPrice = printPrice;
        this.boulevard = boulevard;
        this.typePanneau = typePanneau;
        this.occuped = occuped;
    }*/
}
