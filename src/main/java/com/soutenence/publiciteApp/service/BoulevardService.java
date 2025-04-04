package com.soutenence.publiciteApp.service;

import com.soutenence.publiciteApp.Mapper.BoulevardMapperClass;
import com.soutenence.publiciteApp.Mapper.PanneauMapperClass;
import com.soutenence.publiciteApp.ResponseAndRequest.PageResponse;
import com.soutenence.publiciteApp.entity.Boulevard;
import com.soutenence.publiciteApp.entity.Panneau;
import com.soutenence.publiciteApp.entity.User;
import com.soutenence.publiciteApp.exceptionHandler.UserAlreadyExists;
import com.soutenence.publiciteApp.repository.BoulevardRepositorie;
import com.soutenence.publiciteApp.repository.PanneauRepositorie;
import com.soutenence.publiciteApp.validationObjet.BoulevarRequest;
import com.soutenence.publiciteApp.validationObjet.BoulevardResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BoulevardService {

    private final BoulevardMapperClass boulevardMapperClass;
    private final BoulevardRepositorie boulevardRepositorie;
    private final PanneauRepositorie panneauRepositorie;
    private final PanneauMapperClass panneauMapperClass;
    public BoulevardService(BoulevardMapperClass boulevardMapperClass, BoulevardRepositorie boulevardRepositorie, PanneauRepositorie panneauRepositorie, PanneauMapperClass panneauMapperClass) {
        this.boulevardMapperClass = boulevardMapperClass;
        this.boulevardRepositorie = boulevardRepositorie;
        this.panneauRepositorie = panneauRepositorie;
        this.panneauMapperClass = panneauMapperClass;
    }

    public Long save(BoulevarRequest request, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Boulevard Existboulevard = boulevardRepositorie.findByName(request.name()) ;
if (Existboulevard!=null){
    throw new UserAlreadyExists("Ce boulevard existe déjà");
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
    public PageResponse<BoulevardResponse> findAllBetween2Dates(LocalDate localDate1, LocalDate localDate2, int page, int size) {

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

    public BoulevardResponse getBoulByPanneau(Long panneauId){
        Panneau panneau  = this.panneauRepositorie.findById(panneauId)
                .orElseThrow(()-> new EntityNotFoundException("Ce panneau n'existe pas"));
        Boulevard boulevard = this.boulevardRepositorie.findById(panneau.getBoulevard().getId());
        return this.boulevardMapperClass.ToBoulevardResponse(boulevard);
    }
}
