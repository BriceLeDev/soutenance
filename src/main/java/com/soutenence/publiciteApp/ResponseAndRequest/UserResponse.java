package com.soutenence.publiciteApp.ResponseAndRequest;

import com.soutenence.publiciteApp.entity.Role;
import jakarta.persistence.Column;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;


public record UserResponse(
         long id,
         String email,
         String nonUtilisateur,
         String numero,
         boolean fidelisation,
         boolean accountLocked,
         boolean enabled,
         List<Role> roleList,
         LocalDateTime createdAT,
         LocalDateTime updateAt
) {
}
