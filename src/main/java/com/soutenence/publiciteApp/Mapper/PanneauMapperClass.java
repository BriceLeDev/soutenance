package com.soutenence.publiciteApp.Mapper;

import com.soutenence.publiciteApp.ResponseAndRequest.PanneauResponse;
import com.soutenence.publiciteApp.ResponseAndRequest.PanneauRquest;
import com.soutenence.publiciteApp.entity.Panneau;
import org.springframework.stereotype.Service;

@Service
public class PanneauMapperClass {
    public Panneau ToPanneau(PanneauRquest panneauRquest) {


        return Panneau.builder()

                .localisation(panneauRquest.localisation())
                .taille(panneauRquest.taille())
                .printPrice(panneauRquest.printPrice())
                .prixMensuel(panneauRquest.prixMensuel())
                .NbreFace(panneauRquest.nbRFace())
                .build();
    }

    public PanneauResponse ToPanneauResponse(Panneau panneau) {

        return PanneauResponse.builder()
                .id(panneau.getId())
                .localisation(panneau.getLocalisation())
                .taille(panneau.getTaille())
                .prixMensuel(panneau.getPrixMensuel())
                .printPrice(panneau.getPrintPrice())
                .occuped(panneau.isOccuped())
                .nbrFace(panneau.getNbreFace())
                .boulevardName(panneau.getBoulevard().getName())
                .typePanneauLibele(panneau.getTypePanneau().getLibelet())
                .build();
    }
}
