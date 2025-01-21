package com.soutenence.publiciteApp.service;

import com.soutenence.publiciteApp.Mapper.BoulevardMapperClass;
import com.soutenence.publiciteApp.ResponseAndRequest.PageResponse;
import com.soutenence.publiciteApp.entity.Boulevard;
import com.soutenence.publiciteApp.entity.User;
import com.soutenence.publiciteApp.repository.BoulevardRepositorie;
import com.soutenence.publiciteApp.validationObjet.BoulevarRequest;
import com.soutenence.publiciteApp.validationObjet.BoulevardResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoulevardService {

    private final BoulevardMapperClass boulevardMapperClass;
    private final BoulevardRepositorie boulevardRepositorie;

    public BoulevardService(BoulevardMapperClass boulevardMapperClass, BoulevardRepositorie boulevardRepositorie) {
        this.boulevardMapperClass = boulevardMapperClass;
        this.boulevardRepositorie = boulevardRepositorie;
    }

    public  Integer save(BoulevarRequest request, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Boulevard Existboulevard = boulevardRepositorie.findByName(request.name()) ;
if (Existboulevard!=null){
    throw new RuntimeException("Ce boulevard existe déjà");
}

        Boulevard  boulevard = boulevardMapperClass.ToBoulevard(request);
        return boulevardRepositorie.save(boulevard).getId();

    }

    public BoulevardResponse findById(Integer boulevardId) {
        return boulevardRepositorie.findById(boulevardId)
                .map(boulevardMapperClass::ToBoulevardResponse)
                .orElseThrow(()->new EntityNotFoundException("Ce boulevard n'existe pas"));
    }

    //liste des boulevard en pagination
    public PageResponse<BoulevardResponse> findAll(int page, int size) {

        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt"));
        Page<Boulevard> pageBoulevard = boulevardRepositorie.findAll(pageable);
        List<BoulevardResponse> boulevardResponseList = pageBoulevard.stream()
                .map(boulevardMapperClass::ToBoulevardResponse)
                .toList();
        return new PageResponse<>(
                boulevardResponseList,
                pageBoulevard.getNumber(),
                pageBoulevard.getSize(),
                pageBoulevard.getTotalElements(),
                pageBoulevard.getTotalPages(),
                pageBoulevard.isFirst(),
                pageBoulevard.isLast()

        );
    }

    public void deleteBoulevard(Integer boulevardId) {

        this.boulevardRepositorie.delete(this.boulevardRepositorie
                .findById(boulevardId).orElseThrow(()->new EntityNotFoundException("Ce boulevard n existe pas!")));
    }

    public BoulevardResponse updateBoulevard(Integer boulevardId, BoulevarRequest request) {
        Boulevard boulevard=this.boulevardRepositorie
                .findById(boulevardId).orElseThrow(()->new EntityNotFoundException("Ce boulevard n existe pas!"));
        boulevard.setName(request.name());
        boulevard.setNombreDePanneau(boulevard.getNombreDePanneau());

        this.boulevardRepositorie.save(boulevard);
        return boulevardMapperClass.ToBoulevardResponse(boulevard);
    }
}
