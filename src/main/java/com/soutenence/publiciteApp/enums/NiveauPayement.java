package com.soutenence.publiciteApp.enums;

public enum NiveauPayement {
    TOTAL("Total"),
    PARTIEL("Partiel");

    private final String description;

    NiveauPayement(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
