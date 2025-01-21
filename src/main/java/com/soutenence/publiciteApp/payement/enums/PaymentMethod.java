package com.soutenence.publiciteApp.payement.enums;

public enum PaymentMethod {
    ALL("All payment methods"),
    MOBILE_MONEY("Mobile money"),
    CREDIT_CARD("Credit card"),
    WALLET("Wallet");

    private final String description;

    PaymentMethod(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static PaymentMethod fromDescription(String description) {
        for (PaymentMethod method : PaymentMethod.values()) {
            if (method.description.equalsIgnoreCase(description)) {
                return method;
            }
        }
        throw new IllegalArgumentException("Description inconnue : " + description);
    }
}
