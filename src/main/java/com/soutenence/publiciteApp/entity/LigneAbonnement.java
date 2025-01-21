package com.soutenence.publiciteApp.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;


@Entity
@SuperBuilder
public class LigneAbonnement extends BaseEntity{


    @ManyToOne
    @JoinColumn(name = "abonnement_id")
    private Abonnement abonnement;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "panneau_id")
    private Panneau panneau;

    private LocalDate dateFin;
    private LocalDate dateDebut;

    public LigneAbonnement(BaseEntityBuilder<?, ?> b) {
        super(b);
    }

    public LigneAbonnement() {
    }

    public LigneAbonnement(int id, LocalDate createdAt, LocalDate updateAt, int createdBy, int updateBy) {
        super(id, createdAt, updateAt, createdBy, updateBy);
    }

    public LigneAbonnement(BaseEntityBuilder<?, ?> b, Abonnement abonnement, Panneau panneau, LocalDate dateFin, LocalDate dateDebut) {
        super(b);
        this.abonnement = abonnement;
        this.panneau = panneau;
        this.dateFin = dateFin;
        this.dateDebut = dateDebut;
    }

    public LigneAbonnement(Abonnement abonnement, Panneau panneau, LocalDate dateFin, LocalDate dateDebut) {
        this.abonnement = abonnement;
        this.panneau = panneau;
        this.dateFin = dateFin;
        this.dateDebut = dateDebut;
    }

    public LigneAbonnement(int id, LocalDate createdAt, LocalDate updateAt, int createdBy, int updateBy, Abonnement abonnement, Panneau panneau, LocalDate dateFin, LocalDate dateDebut) {
        super(id, createdAt, updateAt, createdBy, updateBy);
        this.abonnement = abonnement;
        this.panneau = panneau;
        this.dateFin = dateFin;
        this.dateDebut = dateDebut;
    }

    public Abonnement getAbonnement() {
        return abonnement;
    }

    public void setAbonnement(Abonnement abonnement) {
        this.abonnement = abonnement;
    }

    public Panneau getPanneau() {
        return panneau;
    }

    public void setPanneau(Panneau panneau) {
        this.panneau = panneau;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }
}
