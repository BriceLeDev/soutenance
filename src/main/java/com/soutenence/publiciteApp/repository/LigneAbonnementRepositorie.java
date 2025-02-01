package com.soutenence.publiciteApp.repository;

import com.soutenence.publiciteApp.ResponseAndRequest.LigneAbonnementResponse;
import com.soutenence.publiciteApp.entity.Abonnement;
import com.soutenence.publiciteApp.entity.LigneAbonnement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LigneAbonnementRepositorie  extends JpaRepository<LigneAbonnement,Integer> {
    List<LigneAbonnement> findByAbonnement(Abonnement ab);

    List<LigneAbonnement> findAllByAbonnement(Abonnement abonnement);
}
