package com.soutenence.publiciteApp.payement.enums;

public enum TransactionStatus {
    SUCCESS("00", "La transaction est succès"),
    CREATED("201", "Transaction initialisée"),
    PAYMENT_FAILED("600", "Le paiement a échoué"),
    INSUFFICIENT_BALANCE("602", "Le solde du mobileMoney est inférieur au montant à payer"),
    OTP_CODE_ERROR("604", "Le code OTP saisi est incorrect"),
    MINIMUM_REQUIRED_FIELDS("608", "Dans la description, vous trouverez des details"),
    INCORRECT_SETTINGS("606", "Ce message d'erreur est propre à l'utilisation de l'opérateur Orange Money.Voici les causes de cet échec:\n" +
            "1- l'utilisateur a saisi un mauvais code OTP\n" +
            "2- L'utilisateur n'a pas suffisamment de solde\n" +
            "3- une demande de débit du même numéro et même montant a été initialiser dans un laps de temps très court (Par mesure de sécurité, l'opérateur fait échoué la transaction)"),
    AUTH_NOT_FOUND("609", "Apikey introuvable"),
    WAITING_CUSTOMER_TO_VALIDATE("623", "Paiement en attente de confirmation"),
    ERROR_PROCESSING_REQUEST("624", "An error occurred while processing the request\n" +
            "1- Récupérer l'apikey correcte dans votre back-office(menu integration)\n" +
            "2- La variable doit être false"),
    ABONNEMENT_OR_TRANSACTIONS_EXPIRED("625", "Le service a expiré"),
    TRANSACTION_CANCEL("627", "Transaction échec"),
    WAITING_CUSTOMER_PAYMENT("662", "Paiement en attente de confirmation"),
    WAITING_CUSTOMER_OTP_CODE("663", "En attente du code OTP du client");

    private final String code;
    private final String description;

    TransactionStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static TransactionStatus fromCode(String code) {
        for (TransactionStatus status : TransactionStatus.values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Code inconnu : " + code);
    }
}

