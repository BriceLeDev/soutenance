package com.soutenence.publiciteApp.Mapper;

import com.soutenence.publiciteApp.ResponseAndRequest.RoleRequest;
import com.soutenence.publiciteApp.ResponseAndRequest.RoleRespons;
import com.soutenence.publiciteApp.entity.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapperClass {
    public Role ToRole(RoleRequest roleRequest) {
        return  Role.builder()
                .name(roleRequest.name())
                .users(roleRequest.userList())
                .build();
    }

    public RoleRespons ToRoleResponse(Role role) {
        return new RoleRespons(
                role.getId(),
                role.getName(),
                role.getUsers()
        );
    }
}
