package com.soutenence.publiciteApp.payement.enums;

public enum Currency {

        XOF("West African CFA Franc"),
        XAF("Central African CFA Franc"),
        CDF("Congolese Franc"),
        GNF("Guinean Franc"),
        USD("United States Dollar");

        private final String description;

        // Constructeur pour l'énumération
        Currency(String description) {
            this.description = description;
        }

        // Méthode pour obtenir la description de la devise
        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return name() + " (" + description + ")";
        }


}
