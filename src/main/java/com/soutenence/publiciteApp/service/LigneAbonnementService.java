package com.soutenence.publiciteApp.service;

import com.soutenence.publiciteApp.Mapper.LigneAbonnementMapperClass;
import com.soutenence.publiciteApp.ResponseAndRequest.LigneAbonnementResponse;
import com.soutenence.publiciteApp.entity.Abonnement;
import com.soutenence.publiciteApp.repository.AbonnementRepositorie;
import com.soutenence.publiciteApp.repository.LigneAbonnementRepositorie;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LigneAbonnementService {

    private final LigneAbonnementRepositorie ligneAbonnementRepositorie;
    private final AbonnementRepositorie abonnementRepositorie;
    private final LigneAbonnementMapperClass ligneAbonnementMapperClass;
    public LigneAbonnementService(LigneAbonnementRepositorie ligneAbonnementRepositorie, AbonnementRepositorie abonnementRepositorie, LigneAbonnementMapperClass ligneAbonnementMapperClass) {
        this.ligneAbonnementRepositorie = ligneAbonnementRepositorie;
        this.abonnementRepositorie = abonnementRepositorie;
        this.ligneAbonnementMapperClass = ligneAbonnementMapperClass;

    }


    public List<LigneAbonnementResponse> getAllLine(Long abonnementId) {

        Abonnement abonnement = this.abonnementRepositorie.findById(abonnementId).orElseThrow(
                () -> new EntityNotFoundException("Cet Abonnement n existe pas"));

        return ligneAbonnementRepositorie.findAllByAbonnement(abonnement)
                .stream()
                .map(ligneAbonnementMapperClass::ToLigneAbonnementResponse)
                .toList();
    }
}
