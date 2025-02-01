package com.soutenence.publiciteApp.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@SuperBuilder

@Entity
public class TypePanneau extends BaseEntity{

    private  String libelet;
    @JsonManagedReference
    @OneToMany(mappedBy = "typePanneau" , fetch = FetchType.EAGER)
    private List<Panneau> panneaux;

    public TypePanneau(BaseEntityBuilder<?, ?> b) {
        super(b);
    }

    public TypePanneau() {
    }

    public TypePanneau(Long id, LocalDate createdAt, LocalDate updateAt, Integer createdBy, Integer updateBy) {
        super(id, createdAt, updateAt, createdBy, updateBy);
    }

    public TypePanneau(BaseEntityBuilder<?, ?> b, String libelet, List<Panneau> panneaux) {
        super(b);
        this.libelet = libelet;
        this.panneaux = panneaux;
    }

    public String getLibelet() {
        return libelet;
    }

    public void setLibelet(String libelet) {
        this.libelet = libelet;
    }

    public List<Panneau> getPanneaux() {
        return panneaux;
    }

    public void setPanneaux(List<Panneau> panneaux) {
        this.panneaux = panneaux;
    }

    public TypePanneau(String libelet, List<Panneau> panneaux) {
        this.libelet = libelet;
        this.panneaux = panneaux;
    }

    public TypePanneau(Long id, LocalDate createdAt, LocalDate updateAt, Integer createdBy, Integer updateBy, String libelet, List<Panneau> panneaux) {
        super(id, createdAt, updateAt, createdBy, updateBy);
        this.libelet = libelet;
        this.panneaux = panneaux;
    }
}
