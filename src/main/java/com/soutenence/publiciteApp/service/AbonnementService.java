package com.soutenence.publiciteApp.service;

import com.soutenence.publiciteApp.Mapper.AbonnementMapperClass;
import com.soutenence.publiciteApp.ResponseAndRequest.AbonnementRequest;
import com.soutenence.publiciteApp.ResponseAndRequest.AbonnementResponse;
import com.soutenence.publiciteApp.ResponseAndRequest.PageResponse;
import com.soutenence.publiciteApp.entity.*;
import com.soutenence.publiciteApp.payement.entite.Transaction;
import com.soutenence.publiciteApp.payement.repositories.TransactionRepository;
import com.soutenence.publiciteApp.repository.AbonnementRepositorie;
import com.soutenence.publiciteApp.repository.LigneAbonnementRepositorie;
import com.soutenence.publiciteApp.repository.PanneauRepositorie;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AbonnementService {

    private final AbonnementRepositorie abonnementRepositorie;
    private final AbonnementMapperClass abonnementMapperClass;
    private final PanneauRepositorie  panneauRepositorie;
    private final LigneAbonnementRepositorie ligneAbonnementRepositorie;
    private final TransactionRepository transactionRepository;

    public AbonnementService(AbonnementRepositorie abonnementRepositorie, AbonnementMapperClass abonnementMapperClass, PanneauRepositorie panneauRepositorie, LigneAbonnementRepositorie ligneAbonnementRepositorie, TransactionRepository transactionRepository) {
        this.abonnementRepositorie = abonnementRepositorie;
        this.abonnementMapperClass = abonnementMapperClass;
        this.panneauRepositorie = panneauRepositorie;
        this.ligneAbonnementRepositorie = ligneAbonnementRepositorie;
        this.transactionRepository = transactionRepository;
    }

    public Integer saveAbonnement(AbonnementRequest abonnementRequest, Authentication authentication) {
        User user = ((User)authentication.getPrincipal());

        if (abonnementRequest.dateDebut().isAfter(abonnementRequest.dateFin())){
            throw new RuntimeException("La date de fin doit être après la date de début");
        }
        Abonnement abonnement = abonnementMapperClass.ToAbonnement(abonnementRequest);
/*for (int i = 0; i < abonnementRequest.panneau().size(); i++) {

            Panneau panneau = panneauRepositorie.findById(abonnementRequest.id())
                    .orElseThrow(()-> new EntityNotFoundException("Ce panneau n existe pas"));
            panneau.setOccuped(true);
            panneauRepositorie.save(panneau);
        }*/
         abonnement.setUser(user);
         abonnementRepositorie.save(abonnement);

        //Ne pas oublier de gérer cet exception
        /*if (abonnementRequest.Panneau() == null || abonnementRequest.Panneau().isEmpty()) {

        }*/
        List<Integer> lesPanneaux = abonnementRequest.Panneau();
        for (Integer panneau : lesPanneaux ){
            Panneau myPann =panneauRepositorie.findById(panneau).orElseThrow(()-> new EntityNotFoundException("Ce panneau n existe pas"));
            LigneAbonnement ligneAbonnement = LigneAbonnement.builder()
                    .abonnement(abonnement)
                    .dateFin(abonnement.getDateFin())
                    .dateDebut(abonnement.getDateDebut())
                    .panneau(myPann)
                    .build();
            myPann.setOccuped(true);
            panneauRepositorie.save(myPann);
            ligneAbonnementRepositorie.save(ligneAbonnement);

        }

        return abonnement.getId();
    }

    public AbonnementResponse getAbonnementById(Integer abonnementId) {

        return abonnementRepositorie.findById(abonnementId)
                .map(abonnementMapperClass::ToAbonnementResponse)
                .orElseThrow(()-> new EntityNotFoundException("Cet abonnement n existe pas"));

    }

    public PageResponse<AbonnementResponse> getAllAbonnement(int page, int size) {
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt"));
        Page<Abonnement> pageAbonnement = abonnementRepositorie.findAll(pageable);
        List<AbonnementResponse> abonnementResponseList = pageAbonnement.stream()
                .map(abonnementMapperClass::ToAbonnementResponse)
                .toList();
        return new PageResponse<>(
                abonnementResponseList,
                pageAbonnement.getNumber(),
                pageAbonnement.getSize(),
                pageAbonnement.getTotalElements(),
                pageAbonnement.getTotalPages(),
                pageAbonnement.isFirst(),
                pageAbonnement.isLast()

        );
    }

    public PageResponse<AbonnementResponse> getAbonnementByOwner(Integer page, Integer size, Integer userId) {

        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt"));
        Page<Abonnement> pageAbonnement = abonnementRepositorie.findAllByOwner(pageable,userId);
        List<AbonnementResponse> abonnementResponseList = pageAbonnement.stream()
                .map(abonnementMapperClass::ToAbonnementResponse)
                .toList();
        return new PageResponse<>(
                abonnementResponseList,
                pageAbonnement.getNumber(),
                pageAbonnement.getSize(),
                pageAbonnement.getTotalElements(),
                pageAbonnement.getTotalPages(),
                pageAbonnement.isFirst(),
                pageAbonnement.isLast()

        );
    }

    public PageResponse<AbonnementResponse> getAllAbonnementExpired(int page, int size){
        LocalDate today = LocalDate.now();
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt"));
        Page<Abonnement> PageExpiredAbonnement = this.abonnementRepositorie.findByDateFinBefore(pageable,today);

        List<AbonnementResponse> abonnementResponseList = PageExpiredAbonnement.stream()
                .map(abonnementMapperClass::ToAbonnementResponse)
                .toList();
        return new PageResponse<>(
                abonnementResponseList,
                PageExpiredAbonnement.getNumber(),
                PageExpiredAbonnement.getSize(),
                PageExpiredAbonnement.getTotalElements(),
                PageExpiredAbonnement.getTotalPages(),
                PageExpiredAbonnement.isFirst(),
                PageExpiredAbonnement.isLast()

        );
    }

    public PageResponse<AbonnementResponse> getAllAbonnementExpiredById(int id,int page,int size){
        LocalDate today = LocalDate.now();
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt"));
        Page<Abonnement> PageExpiredAbonnement = this.abonnementRepositorie.findByUserAndDateFinBefore(pageable,today,id);

        List<AbonnementResponse> abonnementResponseList = PageExpiredAbonnement.stream()
                .map(abonnementMapperClass::ToAbonnementResponse)
                .toList();
        return new PageResponse<>(
                abonnementResponseList,
                PageExpiredAbonnement.getNumber(),
                PageExpiredAbonnement.getSize(),
                PageExpiredAbonnement.getTotalElements(),
                PageExpiredAbonnement.getTotalPages(),
                PageExpiredAbonnement.isFirst(),
                PageExpiredAbonnement.isLast()
        );

    }

  @Scheduled(fixedRate = 5000)
   public  void setPanneauBusy(){
        LocalDate today = LocalDate.now();
        List<Abonnement> abonnements = this.abonnementRepositorie.findByDateFinAfter(today);

        for (Abonnement ab : abonnements ){
            //Définir l abonnement comme étant occupé
            ab.setActif(true);
            List<LigneAbonnement> ligneAbonnements = this.ligneAbonnementRepositorie.findByAbonnement(ab);
            for(LigneAbonnement lab : ligneAbonnements){
                Panneau panneau = this.panneauRepositorie.findById(lab.getPanneau().getId())
                        .orElseThrow(()-> new EntityNotFoundException("Aucun élément trouvé"));
                panneau.setOccuped(true);
                panneauRepositorie.save(panneau);
            }
        }
    }
  @Scheduled(fixedRate = 5000)
   public  void setPanneauFree(){
        LocalDate today = LocalDate.now();
        List<Abonnement> abonnements = this.abonnementRepositorie.findByDateFinBefore(today);

        for (Abonnement ab : abonnements ){
            //Définir l abonnement comme étant expiré
            ab.setActif(false);
            List<LigneAbonnement> ligneAbonnements = this.ligneAbonnementRepositorie.findByAbonnement(ab);
            for(LigneAbonnement lab : ligneAbonnements){
                Panneau panneau = this.panneauRepositorie.findById(lab.getPanneau().getId())
                        .orElseThrow(()-> new EntityNotFoundException("Aucun élément trouvé"));
                panneau.setOccuped(false);
                panneauRepositorie.save(panneau);
            }
        }
    }

    public Transaction getTransactionById(String id ){
        return this.transactionRepository.findByTransactionId(id);
    }


}

