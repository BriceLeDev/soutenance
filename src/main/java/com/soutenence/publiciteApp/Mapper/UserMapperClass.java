package com.soutenence.publiciteApp.Mapper;

import com.soutenence.publiciteApp.ResponseAndRequest.UserResponse;
import com.soutenence.publiciteApp.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapperClass {



    public UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getNonUtilisateur(),
                user.getNumero(),
                user.isFidelisation(),
                user.isAccountLocked(),
                user.isEnabled(),
                user.getRoles(),
                user.getCreatedAT(),
                user.getUpdateAt()
        );
    }
}
