package com.soutenence.publiciteApp.payement.service;

import com.soutenence.publiciteApp.Mapper.FactureMapperClass;
import com.soutenence.publiciteApp.ResponseAndRequest.FactureResponse;
import com.soutenence.publiciteApp.payement.entite.Facture;
import com.soutenence.publiciteApp.payement.repositories.FactureRepository;
import com.soutenence.publiciteApp.payement.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class FactureService {

    private final FactureRepository factureRepository;
    private final TransactionRepository transactionRepository;
    private final FactureMapperClass factureMapperClass;
    public FactureService(FactureRepository factureRepository, TransactionRepository transactionRepository, FactureMapperClass factureMapperClass) {
        this.factureRepository = factureRepository;
        this.transactionRepository = transactionRepository;
        this.factureMapperClass = factureMapperClass;
    }


    public FactureResponse getFactureByTrans(String transId) {
        Facture facture = this.factureRepository.findByTransaction(transId);
        return factureMapperClass.ToFactureResponse(facture);
    }

    public FactureResponse getFactureByAbonnementid(long abonnementId) {

        Facture facture = this.factureRepository.findByAbonnementId(abonnementId);
        return factureMapperClass.ToFactureResponse(facture);
    }
}
