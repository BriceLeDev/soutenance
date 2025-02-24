package com.soutenence.publiciteApp.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@SuperBuilder

@Entity
public class TypePanneau{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String libelet;
    @JsonManagedReference
    @OneToMany(mappedBy = "typePanneau" , fetch = FetchType.EAGER)
    private List<Panneau> panneaux;


    public TypePanneau() {
    }

    public TypePanneau(String libelet, List<Panneau> panneaux) {
        this.libelet = libelet;
        this.panneaux = panneaux;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

}
