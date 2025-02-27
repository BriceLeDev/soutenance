package com.soutenence.publiciteApp.Mapper;

import com.soutenence.publiciteApp.payement.Response.TransactionResponse;
import com.soutenence.publiciteApp.payement.entite.Transaction;
import org.springframework.stereotype.Service;

@Service
public class TransactionMapperClass {

    public TransactionResponse ToMessageResponse(Transaction transaction){
        return TransactionResponse.builder()
                .userEmail(transaction.getAbonnement().getUser().getEmail())
                .username(transaction.getAbonnement().getUser().getUsername())
                .status(transaction.getStatus())
                .amount(transaction.getAmount())
                .localDateTimeTrans(transaction.getDateTrans())
                .currency(transaction.getCurrency())
                .abonnementId(transaction.getAbonnement().getId())
                .build();
    }
}
