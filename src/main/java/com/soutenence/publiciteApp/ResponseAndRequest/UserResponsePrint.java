package com.soutenence.publiciteApp.ResponseAndRequest;

import com.soutenence.publiciteApp.entity.Role;

import java.time.LocalDateTime;
import java.util.List;


public record UserResponsePrint(

         String email,
         String nonUtilisateur,
         String numero,
         boolean fidelisation,
         boolean accountLocked

) {
}
