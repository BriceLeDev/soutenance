package com.soutenence.publiciteApp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@SuperBuilder
public class Image extends BaseEntity {
 //93 04 03 92 FI / CR 200
    private String nomImage;

    @ManyToOne
    @JoinColumn(name = "abonnement_id", nullable = false)
    private Abonnement  abonnement;

    private  LocalDate dateDebut;
    private  LocalDate dateFin;
    @OneToMany(mappedBy = "theImage")
    private List<LigneAbonnement> ligneAbonnements;
/*
    public Image(BaseEntityBuilder<?, ?> b) {
        super(b);
    }

    public Image() {
    }

    public Image(Long id, LocalDate createdAt, LocalDate updateAt, int createdBy, int updateBy) {
        super(id, createdAt, updateAt, createdBy, updateBy);
    }

    public Image(BaseEntityBuilder<?, ?> b, String nomImage, Abonnement abonnement) {
        super(b);
        this.nomImage = nomImage;
        this.abonnement = abonnement;
    }

    public Image(String nomImage, Abonnement abonnement) {
        this.nomImage = nomImage;
        this.abonnement = abonnement;
    }

    public Image(Long id, LocalDate createdAt, LocalDate updateAt, int createdBy, int updateBy, String nomImage, Abonnement abonnement) {
        super(id, createdAt, updateAt, createdBy, updateBy);
        this.nomImage = nomImage;
        this.abonnement = abonnement;
    }

    public String getNomImage() {
        return nomImage;
    }

    public void setNomImage(String nomImage) {
        this.nomImage = nomImage;
    }

    public Abonnement getAbonnement() {
        return abonnement;
    }

    public void setAbonnement(Abonnement abonnement) {
        this.abonnement = abonnement;
    }*/
}
