package com.soutenence.publiciteApp.payement.request;

import com.soutenence.publiciteApp.payement.enums.Currency;
import com.soutenence.publiciteApp.payement.enums.PaymentMethod;
import lombok.Builder;
@Builder
public class LinkPayementRequest {

    private String apikey;
    private String site_id;
    private String transaction_id;
    private Integer amount; // Le montant de la transaction (doit être un multiple de 5)
    private Currency currency; // La devise monétaire (XOF, XAF, CDF, GNF, USD)
    private String description;
    private String notify_url; // URL de notification
    private String return_url; // URL de retour
    private PaymentMethod channels;

    // Constructeur sans arguments
    public LinkPayementRequest() {
    }

    // Constructeur avec arguments
    public LinkPayementRequest(String apikey, String site_id, String transaction_id, Integer amount,
                               Currency currency, String description, String notify_url,
                               String return_url, PaymentMethod channels) {
        this.apikey = apikey;
        this.site_id = site_id;
        this.transaction_id = transaction_id;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.notify_url = notify_url;
        this.return_url = return_url;
        this.channels = channels;
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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getReturn_url() {
        return return_url;
    }

    public void setReturn_url(String return_url) {
        this.return_url = return_url;
    }

    public PaymentMethod getChannels() {
        return channels;
    }

    public void setChannels(PaymentMethod channels) {
        this.channels = channels;
    }
}
