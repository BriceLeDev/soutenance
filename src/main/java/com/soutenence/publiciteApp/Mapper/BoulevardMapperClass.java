package com.soutenence.publiciteApp.Mapper;

import com.soutenence.publiciteApp.entity.Boulevard;
import com.soutenence.publiciteApp.validationObjet.BoulevarRequest;
import com.soutenence.publiciteApp.validationObjet.BoulevardResponse;
import org.springframework.stereotype.Service;

@Service
public class BoulevardMapperClass {

    public  Boulevard ToBoulevard(BoulevarRequest request) {

        return Boulevard.builder()
                .name(request.name())
                .build();
    }

    public BoulevardResponse ToBoulevardResponse(Boulevard boulevard) {
        return BoulevardResponse.builder()
                .id(boulevard.getId())
                .name(boulevard.getName())
                .NombreDePanneau(boulevard.getNbrDePanneau())
                .build();
    }
}
