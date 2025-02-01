package com.soutenence.publiciteApp.repository;

import com.soutenence.publiciteApp.entity.Boulevard;
import com.soutenence.publiciteApp.entity.Panneau;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PanneauRepositorie extends JpaRepository<Panneau,Long>{


    Page<Panneau> findByOccupedFalse(Pageable pageable);

    Page<Panneau> findByOccupedTrue(Pageable pageable);

    Page<Panneau> findByBoulevard(Boulevard boulevard1, Pageable pageable);

    @Query(value = "SELECT * FROM panneau p WHERE boulevard_id=:boulevard1 AND occuped=false",nativeQuery = true)
    Page<Panneau> findPanneauLibreByBoulevard(Long boulevard1, Pageable pageable);

    @Query(value = "SELECT * FROM panneau  WHERE boulevard_id=:boulevard1 AND occuped=true",nativeQuery = true)
    Page<Panneau> findPanneauOccupeByBoulevard(Long boulevard1, Pageable pageable);
}
