package com.soutenence.publiciteApp.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.soutenence.publiciteApp.enums.NiveauPayement;
import com.soutenence.publiciteApp.payement.entite.Transaction;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Builder
@Entity
public class Abonnement extends BaseEntity {
    
    private double prix;
    private double mtnPayer;
    private double mtnRest;
    private int duree;
    private LocalDate dateFin;
    private LocalDate dateDebut;
    @Column(name = "date_abn")
    private LocalDate dateAbn;
    private boolean actif;
    private String description;
    @Column(nullable = true)
    private Boolean isAlreadyCheck;
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private NiveauPayement niveauAbn;
    private boolean valid;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;
    @OneToMany(mappedBy = "abonnement", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Transaction> transactionsList;
    @OneToMany(mappedBy = "abonnement", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<LigneAbonnement> ligneAbonnements;

    @OneToMany(mappedBy = "abonnement", fetch = FetchType.LAZY)
    private List<Image> images;


    public long getMontantTotalAbonnement() {
        long montant = 0;
        if (this.ligneAbonnements == null || this.ligneAbonnements.isEmpty()) {
            return montant;
        }
        montant = this.ligneAbonnements.stream()
                .mapToLong(LigneAbonnement->LigneAbonnement.getPanneau().getPrixMensuel())
                .sum();
        return montant;
    }

    public int getDureeAbonnement(){
        if (this.dateFin == null || this.dateDebut == null) {
            throw new IllegalStateException("Les dates ne doivent pas être nulles");
        }
        Period period = Period.between(this.dateDebut,this.dateFin);
        //int mounth = this.dateFin.getMonthValue() - this.getCreatedAt().getMonthValue();
        return period.getMonths();
    }

}
