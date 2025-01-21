package com.soutenence.publiciteApp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@SuperBuilder
@Entity
public class Panneau extends BaseEntity {


    private String localisation;
    private Integer taille;
    private Long prixMensuel;
    private Long printPrice;
    private int NbreFace;
    private boolean occuped ;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "boulevard_id")
    private Boulevard boulevard;

    @JsonBackReference
    @OneToMany(mappedBy = "panneau")
    private List<LigneAbonnement> ligneAbonnements;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "type_id")
    private TypePanneau typePanneau;

   /* public Panneau(BaseEntityBuilder<?, ?> b) {
        super(b);
    }

    public Panneau() {
    }

    public Panneau(int id, LocalDate createdAt, LocalDate updateAt, Integer createdBy, Integer updateBy) {
        super(id, createdAt, updateAt, createdBy, updateBy);
    }

    public Panneau(BaseEntityBuilder<?, ?> b, String localisation, Integer taille, Long prixMensuel, Long printPrice, boolean occuped, Boulevard boulevard, List<LigneAbonnement> ligneAbonnements, TypePanneau typePanneau) {
        super(b);
        this.localisation = localisation;
        this.taille = taille;
        this.prixMensuel = prixMensuel;
        this.printPrice = printPrice;
        this.occuped = occuped;
        this.boulevard = boulevard;
        this.ligneAbonnements = ligneAbonnements;
        this.typePanneau = typePanneau;
    }

    public Panneau(String localisation, Integer taille, Long prixMensuel, Long printPrice, boolean occuped, Boulevard boulevard, List<LigneAbonnement> ligneAbonnements, TypePanneau typePanneau) {
        this.localisation = localisation;
        this.taille = taille;
        this.prixMensuel = prixMensuel;
        this.printPrice = printPrice;
        this.occuped = occuped;
        this.boulevard = boulevard;
        this.ligneAbonnements = ligneAbonnements;
        this.typePanneau = typePanneau;
    }

    public Panneau(int id, LocalDate createdAt, LocalDate updateAt, Integer createdBy, Integer updateBy, String localisation, Integer taille, Long prixMensuel, Long printPrice, boolean occuped, Boulevard boulevard, List<LigneAbonnement> ligneAbonnements, TypePanneau typePanneau) {
        super(id, createdAt, updateAt, createdBy, updateBy);
        this.localisation = localisation;
        this.taille = taille;
        this.prixMensuel = prixMensuel;
        this.printPrice = printPrice;
        this.occuped = occuped;
        this.boulevard = boulevard;
        this.ligneAbonnements = ligneAbonnements;
        this.typePanneau = typePanneau;
    }

    public Long getPrintPrice() {
        return printPrice;
    }

    public void setPrintPrice(Long printPrice) {
        this.printPrice = printPrice;
    }

    public void setTaille(Integer taille) {
        this.taille = taille;
    }

    public void setPrixMensuel(Long prixMensuel) {
        this.prixMensuel = prixMensuel;
    }

    public TypePanneau getTypePanneau() {
        return typePanneau;
    }

    public void setTypePanneau(TypePanneau typePanneau) {
        this.typePanneau = typePanneau;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public int getTaille() {
        return taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    public long getPrixMensuel() {
        return prixMensuel;
    }

    public void setPrixMensuel(long prixMensuel) {
        this.prixMensuel = prixMensuel;
    }

    public boolean isOccuped() {
        return occuped;
    }

    public void setOccuped(boolean occuped) {
        this.occuped = occuped;
    }

    public Boulevard getBoulevard() {
        return boulevard;
    }

    public void setBoulevard(Boulevard boulevard) {
        this.boulevard = boulevard;
    }

    public List<LigneAbonnement> getLigneAbonnements() {
        return ligneAbonnements;
    }

    public void setLigneAbonnements(List<LigneAbonnement> ligneAbonnements) {
        this.ligneAbonnements = ligneAbonnements;
    }*/
}
