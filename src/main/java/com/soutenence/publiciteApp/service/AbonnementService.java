package com.soutenence.publiciteApp.service;

import com.soutenence.publiciteApp.Mapper.AbonnementMapperClass;
import com.soutenence.publiciteApp.ResponseAndRequest.AbonnementRequest;
import com.soutenence.publiciteApp.ResponseAndRequest.AbonnementResponse;
import com.soutenence.publiciteApp.ResponseAndRequest.PageResponse;
import com.soutenence.publiciteApp.entity.*;
import com.soutenence.publiciteApp.enums.TypeMessage;
import com.soutenence.publiciteApp.payement.entite.Transaction;
import com.soutenence.publiciteApp.payement.repositories.TransactionRepository;
import com.soutenence.publiciteApp.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class AbonnementService {

    private final AbonnementRepositorie abonnementRepositorie;
    private final AbonnementMapperClass abonnementMapperClass;
    private final PanneauRepositorie  panneauRepositorie;
    private final LigneAbonnementRepositorie ligneAbonnementRepositorie;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final ImageRepositorie imageRepositorie;
    private final MessageRepositorie messageRepositorie;
    public AbonnementService(AbonnementRepositorie abonnementRepositorie, AbonnementMapperClass abonnementMapperClass, PanneauRepositorie panneauRepositorie, LigneAbonnementRepositorie ligneAbonnementRepositorie, TransactionRepository transactionRepository, UserRepository userRepository, ImageRepositorie imageRepositorie, MessageRepositorie messageRepositorie) {
        this.abonnementRepositorie = abonnementRepositorie;
        this.abonnementMapperClass = abonnementMapperClass;
        this.panneauRepositorie = panneauRepositorie;
        this.ligneAbonnementRepositorie = ligneAbonnementRepositorie;
        this.transactionRepository = transactionRepository;

        this.userRepository = userRepository;
        this.imageRepositorie = imageRepositorie;
        this.messageRepositorie = messageRepositorie;
    }

    public Long saveAbonnement(AbonnementRequest abonnementRequest, Authentication authentication) {
        User user = ((User)authentication.getPrincipal());

        if (abonnementRequest.dateDebut().isAfter(abonnementRequest.dateFin())){
            throw new RuntimeException("La date de fin doit être après la date de début");
        }
        Abonnement abonnement = abonnementMapperClass.ToAbonnement(abonnementRequest);

         abonnement.setUser(user);
         abonnement.setValid(false);
         abonnementRepositorie.save(abonnement);

        List<Long> lesPanneaux = abonnementRequest.Panneau();
        for (Long panneau : lesPanneaux ){
            Panneau myPann =panneauRepositorie.findById(panneau).orElseThrow(()-> new EntityNotFoundException("Ce panneau n existe pas"));
            LigneAbonnement ligneAbonnement = LigneAbonnement.builder()
                    .abonnement(abonnement)
                    .dateFin(abonnement.getDateFin())
                    .dateDebut(abonnement.getDateDebut())
                    .panneau(myPann)
                    .build();
            if (Objects.equals(myPann.getTypePanneau().getLibelet(), "Statique-affiche")){
                myPann.setOccuped(true);
            }
            panneauRepositorie.save(myPann);
            ligneAbonnementRepositorie.save(ligneAbonnement);

        }

        return abonnement.getId();
    }

    public AbonnementResponse getAbonnementById(Long abonnementId) {

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

    public PageResponse<AbonnementResponse> getAbonnementByOwner(Integer page, Integer size, Long userId) {

        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt"));
        User user  = this.userRepository.findById(userId).orElseThrow(()-> new EntityNotFoundException("Cet utilisateur n'existe pas"));
        Page<Abonnement> pageAbonnement = abonnementRepositorie.findAllByUserOrderByDateAbnDesc(pageable,user);
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

    /**
     * Abonnement a venir
     * @param page
     * @param size
     * @return PageResponse<AbonnementResponse>
     */
    public PageResponse<AbonnementResponse> getAllBecomeAbonnement(int page, int size){
        LocalDate today = LocalDate.now();
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt"));
        Page<Abonnement> PageExpiredAbonnement = this.abonnementRepositorie.findByDateDebutBefore(pageable,today);

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

    /**
     * Abonnement à venir dans une semaine
     * @param page
     * @param size
     * @return
     */
    public PageResponse<AbonnementResponse> getAllAbonnementComingSoon(int page, int size){
        LocalDate today = LocalDate.now();
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt"));
        Page<Abonnement> PageExpiredAbonnement = this.abonnementRepositorie.findByDateAbnBefore(pageable,getOneWeekBefore(today));

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

    //les abonnement en cours par owner
    public PageResponse<AbonnementResponse> getAllAbonnementByDateFilterByOwner(int id,int page,int size){
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

    /**
     * Abonnement par date
     *
     * @param page
     * @param size
     * @return
     */
    public PageResponse<AbonnementResponse> getAllAbonnementByDateFilter(LocalDate localDate,int page,int size){
        //LocalDate today = LocalDate.now();
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt"));
        Page<Abonnement> PageExpiredAbonnement = this.abonnementRepositorie.findAllByDateAbn(pageable,localDate);

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

    //abonnement entre deux date
    public PageResponse<AbonnementResponse> getAllAbonnementBetween2Date(LocalDate localDate1, LocalDate localDate2, int page, int size){
        //LocalDate today = LocalDate.now();
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt"));
        Page<Abonnement> PageExpiredAbonnement = this.abonnementRepositorie.findByDateAbnBetween(pageable,localDate1,localDate2);

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

    } //abonnement entre deux date
    public PageResponse<AbonnementResponse> getAbonnementsExpirantCeMois(int page, int size){
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfMonth = today.withDayOfMonth(1);
        LocalDate lastDayOfMonth = today.withDayOfMonth(today.lengthOfMonth());
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt"));
        Page<Abonnement> PageExpiredAbonnement = this.abonnementRepositorie.findAbonnementsExpirantCeMois(pageable,firstDayOfMonth,lastDayOfMonth);

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

    } //abonnement entre deux date
    public PageResponse<AbonnementResponse> getAbonnementsCommençantMoisProchain( int page, int size){
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfNextMonth = today.withDayOfMonth(1).plusMonths(1);
        LocalDate lastDayOfNextMonth = firstDayOfNextMonth.withDayOfMonth(firstDayOfNextMonth.lengthOfMonth());

        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt"));
        Page<Abonnement> PageExpiredAbonnement = this.abonnementRepositorie.findAbonnementsCommençantMoisProchain(pageable,firstDayOfNextMonth,lastDayOfNextMonth);

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
        LocalDate today1 = LocalDate.now();
        List<Abonnement> abonnements = this.abonnementRepositorie.findByDateDebutBeforeAndDateFinAfter(today1,today);

        for (Abonnement ab : abonnements ){
            //Définir l abonnement comme étant occupé
            ab.setActif(true);
            abonnementRepositorie.save(ab);
            List<LigneAbonnement> ligneAbonnements = this.ligneAbonnementRepositorie.findByAbonnement(ab);
            for(LigneAbonnement lab : ligneAbonnements){
                Panneau panneau = this.panneauRepositorie.findById(lab.getPanneau().getId())
                        .orElseThrow(()-> new EntityNotFoundException("Aucun élément trouvé"));

                if (panneau.getTypePanneau().getLibelet().equalsIgnoreCase("Statique-affiche")){
                    panneau.setOccuped(true);
                }
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
            abonnementRepositorie.save(ab);
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
        return this.transactionRepository
                .findByTransactionId(id);
    }


    public void validateAbonnement(Long abonnementId) {
        Abonnement abonnement = this.abonnementRepositorie.findById(abonnementId)
                .orElseThrow(()-> new EntityNotFoundException("Elément non trouvé"));
        abonnement.setValid(true);
        this.abonnementRepositorie.save(abonnement);

        Message message = new Message();
        message.setType(TypeMessage.CONFIRMATION);
        message.setReceiver(abonnement.getUser());
        message.setLocalDateTime(LocalDateTime.now());
        String text  = " Votre abonnement est validé après vérification. Il debutera le : " +abonnement.getDateDebut();
        message.setMessage(text);
        this.messageRepositorie.save(message);

    }
    public void invalidateAbonnement(Long abonnementId) {
        Abonnement abonnement = this.abonnementRepositorie.findById(abonnementId)
                .orElseThrow(()-> new EntityNotFoundException("Elément non trouvé"));
        abonnement.setValid(true);
        this.abonnementRepositorie.save(abonnement);

        log.info("Message creating");
        Message message = new Message();
        message.setType(TypeMessage.CONFIRMATION);
        message.setAbonnement(abonnement);
        message.setReceiver(abonnement.getUser());
        message.setLocalDateTime(LocalDateTime.now());
        String text  = "Votre abonnement n'est pas conforme à notre CGU \n veuillez  le consulter ou veuillez nous contacctez sur +228 99898195";
        message.setMessage(text);
        this.messageRepositorie.save(message);

    }

    public static LocalDate getOneWeekBefore(LocalDate date) {
        return date.minusWeeks(1);
    }

}

