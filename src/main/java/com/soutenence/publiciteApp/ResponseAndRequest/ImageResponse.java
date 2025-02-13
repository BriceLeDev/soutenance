package com.soutenence.publiciteApp.ResponseAndRequest;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageResponse {

    private byte[] picture;
    private  Long panneauId;
    private LocalDate dateDebut;
    private  LocalDate dateFin;

}
