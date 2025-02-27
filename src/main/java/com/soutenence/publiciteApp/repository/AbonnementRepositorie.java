package com.soutenence.publiciteApp.repository;

import com.soutenence.publiciteApp.entity.Abonnement;
import com.soutenence.publiciteApp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AbonnementRepositorie  extends JpaRepository<Abonnement,Long> {

    Page<Abonnement> findAllByUserOrderByDateAbnDesc(Pageable pageable, User user);

    Page<Abonnement> findByDateFinBefore(Pageable pageable,LocalDate today);

    Page<Abonnement> findByDateDebutBefore(Pageable pageable,LocalDate today);

    Page<Abonnement> findByDateAbnBefore(Pageable pageable,LocalDate localDate);

    Page<Abonnement> findByUserAndDateFinBefore(Pageable pageable, LocalDate today, int id);


    List<Abonnement> findByDateFinBefore(LocalDate today);

    List<Abonnement> findByDateFinAfter(LocalDate today);

    Page<Abonnement> findByDateAbnBetween(Pageable pageable ,LocalDate date1, LocalDate date2);

    List<Abonnement> findByDateDebutBeforeAndDateFinAfter(LocalDate today1, LocalDate today);

    List<Abonnement> findAllByCreatedAt(LocalDate localDate);

    Page<Abonnement> findAllByDateAbn(Pageable pageable, LocalDate localDate);

    @Query("SELECT a FROM Abonnement a WHERE a.dateDebut BETWEEN :startOfNextMonth AND :endOfNextMonth")
    Page<Abonnement> findAbonnementsCommençantMoisProchain(Pageable pageable, @Param("startOfNextMonth") LocalDate startOfNextMonth,
                                                           @Param("endOfNextMonth") LocalDate endOfNextMonth);

    @Query("SELECT a FROM Abonnement a WHERE a.dateFin BETWEEN :startOfMonth AND :endOfMonth")
    Page<Abonnement> findAbonnementsExpirantCeMois(Pageable pageable, @Param("startOfMonth") LocalDate startOfMonth,
                                                   @Param("endOfMonth") LocalDate endOfMonth);

}
