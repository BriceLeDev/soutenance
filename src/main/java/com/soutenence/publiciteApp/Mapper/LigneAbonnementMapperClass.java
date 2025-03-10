package com.soutenence.publiciteApp.Mapper;

import com.soutenence.publiciteApp.ResponseAndRequest.AbonnementRequest;
import com.soutenence.publiciteApp.ResponseAndRequest.AbonnementResponse;
import com.soutenence.publiciteApp.ResponseAndRequest.LigneAbonnementResponse;
import com.soutenence.publiciteApp.UtilitiesFiles.FilesUtils;
import com.soutenence.publiciteApp.entity.Abonnement;
import com.soutenence.publiciteApp.entity.LigneAbonnement;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class LigneAbonnementMapperClass {


    public LigneAbonnementResponse ToLigneAbonnementResponse(LigneAbonnement ligneAbonnement){
        return LigneAbonnementResponse.builder()
                .panneauId(ligneAbonnement.getPanneau().getId())
                .boulevardName(ligneAbonnement.getPanneau().getBoulevard().getName())
                .emplacement(ligneAbonnement.getPanneau().getLocalisation())
                .price(ligneAbonnement.getPanneau().getPrixMensuel())
                .printPrice(ligneAbonnement.getPanneau().getPrintPrice())
                .taille(ligneAbonnement.getPanneau().getTaille())
                .type(ligneAbonnement.getPanneau().getTypePanneau().getLibelet())
                .nbrFace(ligneAbonnement.getPanneau().getNbreFace())
                .build();
    }
}
