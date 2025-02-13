package com.soutenence.publiciteApp.ResponseAndRequest;

import com.soutenence.publiciteApp.enums.TypeMessage;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {

    private  String message;
    private String receiverMail;
    private Long abonnementId;
    private LocalDateTime localDateTime;
    private TypeMessage type;
    private LocalDate dateDebAbn;
    private LocalDate dateFinAbn;
    private LocalDate dateAbn;
    private String descriptionAbn;
}
