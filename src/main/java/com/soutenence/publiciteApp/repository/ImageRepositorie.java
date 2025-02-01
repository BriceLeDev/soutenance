package com.soutenence.publiciteApp.repository;

import com.soutenence.publiciteApp.ResponseAndRequest.ImageResponse;
import com.soutenence.publiciteApp.entity.Abonnement;
import com.soutenence.publiciteApp.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageRepositorie extends JpaRepository<Image,Integer>, JpaSpecificationExecutor<Image> {
    Image findByAbonnement(Abonnement abonnement);

    List<Image> findAllByAbonnement(Abonnement abonnement);

    //List<ImageResponse> findAllImageByAbonnement(Abonnement abonnement);
}
