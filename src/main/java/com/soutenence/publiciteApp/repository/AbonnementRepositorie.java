package com.soutenence.publiciteApp.repository;

import com.soutenence.publiciteApp.entity.Abonnement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AbonnementRepositorie  extends JpaRepository<Abonnement,Integer> {

    @Query(value = """
            SELECT abnmt
            FROM Abonnement abnmt
            WHERE abnmt.owner.id=:userId
            """, nativeQuery = true)
    Page<Abonnement> findAllByOwner(Pageable pageable, Integer userId);

    Page<Abonnement> findByDateFinBefore(Pageable pageable,LocalDate today);

    Page<Abonnement> findByUserAndDateFinBefore(Pageable pageable, LocalDate today, int id);


    List<Abonnement> findByDateFinBefore(LocalDate today);

    List<Abonnement> findByDateFinAfter(LocalDate today);
}
