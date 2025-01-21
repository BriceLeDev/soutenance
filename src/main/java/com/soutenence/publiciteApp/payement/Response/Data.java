package com.soutenence.publiciteApp.payement.Response;

public class Data {
    private String paymentToken;
    private String paymentUrl;

    // Getters et Setters
    public String getPaymentToken() {
        return paymentToken;
    }

    public void setPaymentToken(String paymentToken) {
        this.paymentToken = paymentToken;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    @Override
    public String toString() {
        return "Data{" +
                "paymentToken='" + paymentToken + '\'' +
                ", paymentUrl='" + paymentUrl + '\'' +
                '}';
    }
}

