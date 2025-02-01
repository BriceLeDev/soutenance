package com.soutenence.publiciteApp.Mapper;

import com.soutenence.publiciteApp.ResponseAndRequest.AbonnementRequest;
import com.soutenence.publiciteApp.ResponseAndRequest.AbonnementResponse;
import com.soutenence.publiciteApp.entity.Abonnement;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class AbonnementMapperClass {
    public Abonnement ToAbonnement(AbonnementRequest abonnementRequest) {


        return Abonnement.builder()

                .prix(abonnementRequest.prix())
                .dateDebut(abonnementRequest.dateDebut())
                .dateFin(abonnementRequest.dateFin())
                .dateAbn(LocalDate.now())
                .mtnRest(abonnementRequest.prix())
                .build();
    }

    public AbonnementResponse ToAbonnementResponse(Abonnement abonnement){
        return AbonnementResponse.builder()
                .id(abonnement.getId())
                .prix(abonnement.getPrix())
                .mtnPayer(abonnement.getMtnPayer())
                .mtnRest(abonnement.getMtnRest())
                .dateDebut(abonnement.getDateDebut())
                .dateFin(abonnement.getDateFin())
                .description(abonnement.getDescription())
                .dateAbn(abonnement.getDateAbn())
                .duree(abonnement.getDureeAbonnement())
                .nbrJrs(Period.between(abonnement.getDateDebut(),abonnement.getDateFin()).getDays())
                .actif(abonnement.isActif())
                .valid(abonnement.isValid())
                .build();
    }
}
