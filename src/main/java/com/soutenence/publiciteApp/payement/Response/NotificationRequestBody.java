package com.soutenence.publiciteApp.payement.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NotificationRequestBody {

    private String cpm_site_id; // La variable site_id envoyée à l’initialisation
    private String cpm_trans_id; // La variable transaction_id envoyée à l’initialisation
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime cpm_trans_date; // La date et heure de la transaction (ex: 2000-12-21 19:19:00)
    private Double cpm_amount; // Le montant
    private String cpm_currency; // La devise
    private String signature; // Un token, différent du token généré
    private String payment_method; // La méthode de paiement
    private String cel_phone_num; // Le numéro qui a servi à faire le paiement
    private String cpm_phone_prefixe; // Le préfixe du pays
    private String cpm_language; // La langue utilisée pour le paiement
    private String cpm_version; // La version utilisée (ex: V4)
    private String cpm_payment_config; // Le type de paiement (ex: Single)
    private String cpm_page_action; // Le type d'action (ex: Payment)
    private String cpm_custom; // La variable metadata envoyée à l’initialisation
    private String cpm_designation; // La variable designation envoyée à l’initialisation
    private String cpm_error_message; // Le statut de la transaction ou la raison de l'échec

}
