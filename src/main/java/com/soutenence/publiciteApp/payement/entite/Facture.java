package com.soutenence.publiciteApp.payement.entite;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.soutenence.publiciteApp.entity.Abonnement;
import com.soutenence.publiciteApp.entity.BaseEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.lang.model.element.NestingKind;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Facture {

    @Id
    @GeneratedValue
    private int id;
    private String reference;
    private Double mtnTotal;
    private Integer mtnPayer;
    private Double mtnRestant;
    private LocalDate datePayment;
    private LocalDate dateDebAbn;
    private LocalDate dateFinAbn;
    private LocalDate dateAbn;
    private String transaction;


}
