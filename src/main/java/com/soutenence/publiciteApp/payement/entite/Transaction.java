package com.soutenence.publiciteApp.payement.entite;

import com.soutenence.publiciteApp.entity.Abonnement;
import com.soutenence.publiciteApp.payement.enums.Currency;
import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;

import java.util.Date;
import java.util.Objects;

@Entity
@Builder
public class Transaction {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY) // Auto-génère l'ID pour chaque transaction
    private Long id;

    @Column(nullable = false, unique = true)
    private String transactionId;

    @Column(nullable = false)
    private Integer amount; // Le montant de la transaction (doit être un multiple de 5)

    @Column(nullable = false)
    //@Enumerated(EnumType.STRING)
    private Currency currency; // La devise monétaire (XOF, XAF, CDF, GNF, USD)

    @Column(nullable = false)
    private String description;

    //@Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private Date dateTrans;
    @ManyToOne
    @JoinColumn(name = "abonnement")
    private Abonnement  abonnement;


    // Constructeur sans arguments
    public Transaction() {
    }


    // Constructeur avec arguments


    public Transaction(Long id, String transactionId, Integer amount, Currency currency, String description, String status, Date dateTrans, Abonnement abonnement) {
        this.id = id;
        this.transactionId = transactionId;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.status = status;
        this.dateTrans = dateTrans;
        this.abonnement = abonnement;
    }

    // Méthode pour générer le transactionId combiné


    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateTrans() {
        return dateTrans;
    }

    public void setDateTrans(Date dateTrans) {
        this.dateTrans = dateTrans;
    }

    public Abonnement getAbonnement() {
        return abonnement;
    }

    public void setAbonnement(Abonnement abonnement) {
        this.abonnement = abonnement;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id) && Objects.equals(transactionId, that.transactionId) && Objects.equals(amount, that.amount) && currency == that.currency && Objects.equals(description, that.description) && Objects.equals(status, that.status) && Objects.equals(dateTrans, that.dateTrans) && Objects.equals(abonnement, that.abonnement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transactionId, amount, currency, description, status, dateTrans, abonnement);
    }



    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", transactionId='" + transactionId + '\'' +
                ", amount=" + amount +
                ", currency=" + currency +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", dateTrans=" + dateTrans +
                ", abonnement=" + abonnement +
                '}';
    }
}

