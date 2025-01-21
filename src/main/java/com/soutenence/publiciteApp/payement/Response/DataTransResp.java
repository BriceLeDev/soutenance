package com.soutenence.publiciteApp.payement.Response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DataTransResp {

    private String amount;
    private String currency;
    private String status;
    private String payment_method;
    private String description;
    private String metadata;
    private String operator_id;
    private String payment_date;
    private String fund_availability_date;

    // Getters and setters
   /* public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getOperator_id() {
        return operator_id;
    }

    public void setOperator_id(String operator_id) {
        this.operator_id = operator_id;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date;
    }

    public String getFund_availability_date() {
        return fund_availability_date;
    }

    public void setFund_availability_date(String fund_availability_date) {
        this.fund_availability_date = fund_availability_date;
    } */
}
