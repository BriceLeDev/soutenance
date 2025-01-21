package com.soutenence.publiciteApp.ResponseAndRequest;

import com.soutenence.publiciteApp.entity.Role;
import com.soutenence.publiciteApp.entity.User;

import java.util.List;

public record RoleRespons(
        int id,
        String name,
        List<User> userList
) {
}
