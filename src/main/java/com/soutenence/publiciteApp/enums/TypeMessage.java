package com.soutenence.publiciteApp.enums;

public enum TypeMessage {
    CONFIRMATION("Vérification"),
    VERIFICATION("Confirmation");

    private final String description;

    TypeMessage(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
