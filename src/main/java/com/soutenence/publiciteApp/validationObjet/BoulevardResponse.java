package com.soutenence.publiciteApp.validationObjet;

import lombok.Builder;

@Builder
public class BoulevardResponse {

    private Long id;
    private String name;
    private Integer NombreDePanneau;

    public BoulevardResponse() {
    }

    public BoulevardResponse(Long id, String name, Integer nombreDePanneau) {
        this.id = id;
        this.name = name;
        this.NombreDePanneau = nombreDePanneau;
    }

    public Integer getNombreDePanneau() {
        return NombreDePanneau;
    }

    public void setNombreDePanneau(Integer nombreDePanneau) {
        NombreDePanneau = nombreDePanneau;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
