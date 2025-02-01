package com.soutenence.publiciteApp.payement.request;


import com.soutenence.publiciteApp.payement.enums.Currency;
import com.soutenence.publiciteApp.payement.enums.PaymentMethod;

public record TransactionRequest(
        Integer amount, // Le montant de la transaction (doit être un multiple de 5)
        Currency currency, // La devise monétaire (XOF, XAF, CDF, GNF, USD)
        String description,
        String dateTrans, // Vous pouvez également utiliser LocalDate ou LocalDateTime
        PaymentMethod chanel,
        Long abonnementId // ID de l'abonnement, si vous avez une référence à Abonnement
) {
}
