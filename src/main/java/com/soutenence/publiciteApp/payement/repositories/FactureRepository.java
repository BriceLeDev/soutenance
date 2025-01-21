package com.soutenence.publiciteApp.payement.repositories;

import com.soutenence.publiciteApp.payement.entite.Facture;
import com.soutenence.publiciteApp.payement.entite.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactureRepository extends JpaRepository<Facture,Long> {
    Facture findByTransaction(String trans);
}
