package com.soutenence.publiciteApp.repository;

import com.soutenence.publiciteApp.entity.Boulevard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BoulevardRepositorie extends JpaRepository<Boulevard,Integer> {

    public Boulevard findByName(String name);
    public Boulevard findById(Long id);

    public List<Boulevard> findByCreatedAtBetween(LocalDate date1, LocalDate date2);
    //Boulevard findByPanneau(Panneau panneau);
}
