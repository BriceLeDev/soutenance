package com.soutenence.publiciteApp.ResponseAndRequest;

import com.soutenence.publiciteApp.entity.Panneau;
import lombok.Builder;

import java.util.List;
@Builder
public class TypePanResponse {
    private int id;
    private String libelet;
    private List<Panneau> panneau;

    public TypePanResponse() {
    }

    public TypePanResponse(int id, String libelet, List<Panneau> panneau) {
        this.id = id;
        this.libelet = libelet;
        this.panneau = panneau;
    }

    public TypePanResponse(String libelet, List<Panneau> panneau) {
        this.libelet = libelet;
        this.panneau = panneau;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelet() {
        return libelet;
    }

    public void setLibelet(String libelet) {
        this.libelet = libelet;
    }

    public List<Panneau> getPanneau() {
        return panneau;
    }

    public void setPanneau(List<Panneau> panneau) {
        this.panneau = panneau;
    }
}
