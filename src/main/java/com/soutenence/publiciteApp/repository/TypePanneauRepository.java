package com.soutenence.publiciteApp.repository;

import com.soutenence.publiciteApp.entity.TypePanneau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypePanneauRepository extends JpaRepository<TypePanneau,Long> {

    public TypePanneau findByLibelet(String libelet);
}
