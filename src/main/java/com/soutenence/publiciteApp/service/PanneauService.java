package com.soutenence.publiciteApp.service;

import com.soutenence.publiciteApp.Mapper.PanneauMapperClass;
import com.soutenence.publiciteApp.ResponseAndRequest.PageResponse;
import com.soutenence.publiciteApp.ResponseAndRequest.PanneauResponse;
import com.soutenence.publiciteApp.ResponseAndRequest.PanneauRquest;
import com.soutenence.publiciteApp.entity.Boulevard;
import com.soutenence.publiciteApp.entity.Panneau;
import com.soutenence.publiciteApp.entity.TypePanneau;
import com.soutenence.publiciteApp.repository.BoulevardRepositorie;
import com.soutenence.publiciteApp.repository.PanneauRepositorie;
import com.soutenence.publiciteApp.repository.TypePanneauRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PanneauService {

    private  final PanneauRepositorie panneauRepositorie;
    private final PanneauMapperClass panneauMapperClass;
    private  final BoulevardRepositorie boulevardRepositorie;
    private  final BoulevardService boulevardService ;
    private final TypePanneauRepository typePanneauRepository;

    public PanneauService(PanneauRepositorie panneauRepositorie, PanneauMapperClass panneauMapperClass, BoulevardRepositorie boulevardRepositorie, BoulevardService boulevardService, TypePanneauRepository typePanneauRepository) {
        this.panneauRepositorie = panneauRepositorie;
        this.panneauMapperClass = panneauMapperClass;
        this.boulevardRepositorie = boulevardRepositorie;
        this.boulevardService = boulevardService;
        this.typePanneauRepository = typePanneauRepository;
    }

    public Long savePanneau(PanneauRquest panneauRquest) throws IllegalAccessException {

        Boulevard boulevard = boulevardRepositorie.findById(panneauRquest.boulevard_id())
                .orElseThrow(()-> new EntityNotFoundException("Ce boulevard n existe pas"));

        TypePanneau  typePanneau= typePanneauRepository.findByLibelet(panneauRquest.typePanneau());
        if (typePanneau == null){
            throw new IllegalAccessException("Ce type panneau n existe pas");
        }
        Panneau panneau = panneauMapperClass.ToPanneau(panneauRquest);
        panneau.setTypePanneau(typePanneau);
        panneau.setBoulevard(boulevard);
        panneau.setEnabled(true);
        return panneauRepositorie.save(panneau).getId();
    }


    public PanneauResponse findById(Long panneauId) {

        return panneauRepositorie.findById(panneauId)
                .map(panneauMapperClass::ToPanneauResponse)
                .orElseThrow(()-> new EntityNotFoundException("Aucun panneau n à été retrouvé"));
    }

    public PageResponse<PanneauResponse> getAllPanneaux(int page, int size) {
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt"));
        Page<Panneau> panneauPage = panneauRepositorie.findAll(pageable);
        List<PanneauResponse> panneauList = panneauPage.stream()
                .map(panneauMapperClass::ToPanneauResponse)
                .toList();
        return new PageResponse<>(
                panneauList,
                panneauPage.getNumber(),
                panneauPage.getSize(),
                panneauPage.getTotalElements(),
                panneauPage.getTotalPages(),
                panneauPage.isFirst(),
                panneauPage.isLast()
        );
    }

    public PageResponse<PanneauResponse> getAllPanneauxLibre(int page, int size) {

        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt"));
        Page<Panneau> panneauPage = panneauRepositorie.findByOccupedFalse(pageable);
        List<PanneauResponse> panneauList = panneauPage.stream()
                .map(panneauMapperClass::ToPanneauResponse)
                .toList();
        return new PageResponse<>(
                panneauList,
                panneauPage.getNumber(),
                panneauPage.getSize(),
                panneauPage.getTotalElements(),
                panneauPage.getTotalPages(),
                panneauPage.isFirst(),
                panneauPage.isLast()
        );
    }

    public PageResponse<PanneauResponse> getAllPanneauxOccuper(int page, int size) {
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt"));
        Page<Panneau> panneauPage = panneauRepositorie.findByOccupedTrue(pageable);
        List<PanneauResponse> panneauList = panneauPage.stream()
                .map(panneauMapperClass::ToPanneauResponse)
                .toList();
        return new PageResponse<>(
                panneauList,
                panneauPage.getNumber(),
                panneauPage.getSize(),
                panneauPage.getTotalElements(),
                panneauPage.getTotalPages(),
                panneauPage.isFirst(),
                panneauPage.isLast()
        );
    }

    public PanneauResponse updatePanneauById(Long panneauId, PanneauRquest panneauRquest) {
        Boulevard boulevard=this.boulevardRepositorie
                .findById(panneauRquest.boulevard_id()).orElseThrow(()->new EntityNotFoundException("Ce boulevard n existe pas!"));
        Panneau panneau=this.panneauRepositorie
                .findById(panneauId).orElseThrow(()->new EntityNotFoundException("Ce Panneau n'existe pas!"));
        panneau.setLocalisation(panneauRquest.localisation());
        panneau.setPrixMensuel(panneauRquest.prixMensuel());
        panneau.setTaille(panneauRquest.taille());
        panneau.setBoulevard(boulevard);
        this.panneauRepositorie.save(panneau);
        return panneauMapperClass.ToPanneauResponse(panneau);
    }


    public PageResponse<PanneauResponse> getAllPanneauxByBoulevard(String boulevardId, int page, int size) {

        Boulevard boulevard1 = boulevardRepositorie.findByName(boulevardId);

        if(boulevard1==null){
            throw  new EntityNotFoundException("Ce boulevard n existe pas");
        }

        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt"));
        Page<Panneau> panneauPage = panneauRepositorie.findByBoulevard(boulevard1,pageable);
        List<PanneauResponse> panneauList = panneauPage.stream()
                .map(panneauMapperClass::ToPanneauResponse)
                .toList();
        return new PageResponse<>(
                panneauList,
                panneauPage.getNumber(),
                panneauPage.getSize(),
                panneauPage.getTotalElements(),
                panneauPage.getTotalPages(),
                panneauPage.isFirst(),
                panneauPage.isLast()
        );
    }

    public PageResponse<PanneauResponse> getAllPanneauxOccuperByBoulevard(String boulevarName, int page, int size) {

        Boulevard boulevard1 = boulevardRepositorie.findByName(boulevarName);
        if(boulevard1==null){
            throw  new EntityNotFoundException("Ce boulevard n existe pas");
        }

        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt"));
        Page<Panneau> panneauPage = panneauRepositorie.findPanneauOccupeByBoulevard(boulevard1.getId(),pageable);
        List<PanneauResponse> panneauList = panneauPage.stream()
                .map(panneauMapperClass::ToPanneauResponse)
                .toList();
        return new PageResponse<>(
                panneauList,
                panneauPage.getNumber(),
                panneauPage.getSize(),
                panneauPage.getTotalElements(),
                panneauPage.getTotalPages(),
                panneauPage.isFirst(),
                panneauPage.isLast()
        );
    }

    public PageResponse<PanneauResponse> getAllPanneauxLibreByBoulevard(String boulevardId, int page, int size) {

        Boulevard boulevard1 = boulevardRepositorie.findByName(boulevardId);
        if(boulevard1==null){
            throw  new EntityNotFoundException("Ce boulevard n existe pas");
        }

        Pageable pageable = PageRequest.of(page,size, Sort.by("created_at"));
        //System.out.println("ID de l'entité : " + boulevard1.getId());

        Page<Panneau> panneauPage = panneauRepositorie.findPanneauLibreByBoulevard(boulevard1.getId(),pageable);
        List<PanneauResponse> panneauList = panneauPage.stream()
                .map(panneauMapperClass::ToPanneauResponse)
                .toList();
        return new PageResponse<>(
                panneauList,
                panneauPage.getNumber(),
                panneauPage.getSize(),
                panneauPage.getTotalElements(),
                panneauPage.getTotalPages(),
                panneauPage.isFirst(),
                panneauPage.isLast()
        );
    }

    public void deletePanneau(Long panneauId) {
        this.panneauRepositorie.delete(this.panneauRepositorie
                .findById(panneauId).orElseThrow(()->new EntityNotFoundException("Panneau non trouvé!")));
    }

    public void setPanneauEnabled(Long panneauId){
        Panneau panneau  = this.panneauRepositorie.findById(panneauId).orElseThrow(
                ()-> new EntityNotFoundException("Panneau non trouver")

        );
        panneau.setEnabled(false);
        this.panneauRepositorie.save(panneau);
    }
}
