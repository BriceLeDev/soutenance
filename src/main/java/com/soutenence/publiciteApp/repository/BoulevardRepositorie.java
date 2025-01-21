package com.soutenence.publiciteApp.repository;

import com.soutenence.publiciteApp.entity.Boulevard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoulevardRepositorie extends JpaRepository<Boulevard,Integer> {

    public Boulevard findByName(String name);
    public Boulevard findById(int id);
}
