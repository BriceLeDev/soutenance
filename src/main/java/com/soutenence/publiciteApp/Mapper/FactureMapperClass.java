package com.soutenence.publiciteApp.Mapper;

import com.soutenence.publiciteApp.ResponseAndRequest.FactureResponse;
import com.soutenence.publiciteApp.ResponseAndRequest.MessageResponse;
import com.soutenence.publiciteApp.entity.Message;
import com.soutenence.publiciteApp.payement.entite.Facture;
import org.springframework.stereotype.Service;

@Service
public class FactureMapperClass {


    public FactureResponse ToFactureResponse(Facture facture){
        return FactureResponse.builder()
                .id(facture.getId())
                .abonnementId(facture.getAbonnementId())
                .reference(facture.getReference())
                .transaction(facture.getTransaction())
                .mtnRestant(facture.getMtnRestant())
                .mtnPayer(facture.getMtnPayer())
                .mtnTotal(facture.getMtnTotal())
                .datePayment(facture.getDatePayment())
                .dateDebAbn(facture.getDateDebAbn())
                .dateFinAbn(facture.getDateFinAbn())
                .dateAbn(facture.getDateAbn())
                .build();
    }
}
