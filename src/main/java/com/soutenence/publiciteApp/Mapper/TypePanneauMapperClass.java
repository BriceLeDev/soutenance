package com.soutenence.publiciteApp.Mapper;

import com.soutenence.publiciteApp.ResponseAndRequest.TypePanRequest;
import com.soutenence.publiciteApp.ResponseAndRequest.TypePanResponse;
import com.soutenence.publiciteApp.entity.TypePanneau;
import org.springframework.stereotype.Service;

@Service
public class TypePanneauMapperClass {
    public TypePanneau ToTypePanneau(TypePanRequest panneauRquest) {

        return TypePanneau.builder()
                .libelet(panneauRquest.libellet())
                .build();
    }

    public TypePanResponse ToTypePanneauResponse(TypePanneau typePanneau) {

        return TypePanResponse.builder()
                .id(typePanneau.getId())
                .libelet(typePanneau.getLibelet())
                .panneau(typePanneau.getPanneaux())
                .build();
    }
}
