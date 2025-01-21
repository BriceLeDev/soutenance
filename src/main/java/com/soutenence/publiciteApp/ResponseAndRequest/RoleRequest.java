package com.soutenence.publiciteApp.ResponseAndRequest;

import com.soutenence.publiciteApp.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RoleRequest(
        @NotBlank(message = "Saisissez le role de l'utilisateur")
        @NotEmpty(message = "Saisissez le role de l'utilisateur")
        String name,
        List<User> userList
) {
}
