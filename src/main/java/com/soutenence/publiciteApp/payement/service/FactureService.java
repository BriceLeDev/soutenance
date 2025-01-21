package com.soutenence.publiciteApp.payement.service;

import com.soutenence.publiciteApp.payement.entite.Facture;
import com.soutenence.publiciteApp.payement.entite.Transaction;
import com.soutenence.publiciteApp.payement.repositories.FactureRepository;
import com.soutenence.publiciteApp.payement.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class FactureService {

    private final FactureRepository factureRepository;
    private final TransactionRepository transactionRepository;
    public FactureService(FactureRepository factureRepository, TransactionRepository transactionRepository) {
        this.factureRepository = factureRepository;
        this.transactionRepository = transactionRepository;
    }


    public Facture getFactureByTrans(String transId) {
        return this.factureRepository.findByTransaction(transId);
    }
}
