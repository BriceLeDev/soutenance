package com.soutenence.publiciteApp.specification;

import com.soutenence.publiciteApp.entity.Image;
import org.springframework.data.jpa.domain.Specification;

public class ImageSpecification {

    public static Specification<Image> byAbonnement(Integer abonnementId){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("abonnement").get("id"),abonnementId));
    }
}
