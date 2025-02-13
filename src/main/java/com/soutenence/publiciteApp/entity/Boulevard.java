package com.soutenence.publiciteApp.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Entity
@SuperBuilder
public class Boulevard extends BaseEntity {

    @Column(unique = true)
    private String name;
    private Integer NombreDePanneau;

    @JsonManagedReference
    @OneToMany(mappedBy = "boulevard",fetch = FetchType.EAGER)
    private List<Panneau> panneaux;

    public Boulevard() {
    }

    public Boulevard(BaseEntityBuilder<?, ?> b, String name, int nombreDePanneau, List<Panneau> panneaux) {
        super(b);
        this.name = name;
        NombreDePanneau = nombreDePanneau;
        this.panneaux = panneaux;
    }

    public Boulevard(String name, int nombreDePanneau, List<Panneau> panneaux) {
        this.name = name;
        NombreDePanneau = nombreDePanneau;
        this.panneaux = panneaux;
    }

    public Boulevard(Long id, LocalDate createdAt, LocalDate updateAt, int createdBy, int updateBy, String name, int nombreDePanneau, List<Panneau> panneaux) {
        super(id, createdAt, updateAt, createdBy, updateBy);
        this.name = name;
        NombreDePanneau = nombreDePanneau;
        this.panneaux = panneaux;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNombreDePanneau() {
        return NombreDePanneau != null ? NombreDePanneau:0;
    }

    public void setNombreDePanneau(int nombreDePanneau) {
        NombreDePanneau = nombreDePanneau;
    }

    public List<Panneau> getPanneaux() {
        return panneaux;
    }

    public void setPanneaux(List<Panneau> panneaux) {
        this.panneaux = panneaux;
    }


    public Integer getNbrDePanneau(){
        int nbr =0;
        if(this.panneaux == null || this.panneaux.isEmpty()){
            return nbr;
        }
        nbr = this.panneaux.size();
        this.setNombreDePanneau(nbr);
        return nbr;
    }
}
