package com.soutenence.publiciteApp.payement.request;

import lombok.Builder;

@Builder
public class CheckTransactionRequest {

        private String apikey; // Votre apikey
        private String site_id; // Votre site_id
        private String transaction_id; // L'identifiant de la transaction

        // Constructeur sans argument
        public CheckTransactionRequest() {
        }

        // Constructeur avec tous les arguments
        public CheckTransactionRequest(String apikey, String site_id, String transaction_id) {
            this.apikey = apikey;
            this.site_id = site_id;
            this.transaction_id = transaction_id;
        }

        // Getters et Setters
        public String getApikey() {
            return apikey;
        }

        public void setApikey(String apikey) {
            this.apikey = apikey;
        }

        public String getSite_id() {
            return site_id;
        }

        public void setSite_id(String site_id) {
            this.site_id = site_id;
        }

        public String getTransaction_id() {
            return transaction_id;
        }

        public void setTransaction_id(String transaction_id) {
            this.transaction_id = transaction_id;
        }
    }

