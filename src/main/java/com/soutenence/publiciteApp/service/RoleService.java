package com.soutenence.publiciteApp.service;

import com.soutenence.publiciteApp.Mapper.RoleMapperClass;
import com.soutenence.publiciteApp.ResponseAndRequest.RoleRequest;
import com.soutenence.publiciteApp.ResponseAndRequest.RoleRespons;
import com.soutenence.publiciteApp.entity.Role;
import com.soutenence.publiciteApp.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapperClass roleMapperClass;

    public RoleService(RoleRepository roleRepository, RoleMapperClass roleMapperClass) {
        this.roleRepository = roleRepository;
        this.roleMapperClass = roleMapperClass;
    }

    public Integer addRole(RoleRequest roleRequest) {
        Role role = roleMapperClass.ToRole(roleRequest);
        return this.roleRepository.save(role).getId();
    }

    public RoleRespons getRole(int roleId) {

        return this.roleRepository.findById(roleId)
                .map(roleMapperClass::ToRoleResponse)
                .orElseThrow(()->new EntityNotFoundException("Ce role n existe pas!"));
    }

    public RoleRespons update(int roleId, RoleRequest roleRequest) {
        Role  role = this.roleRepository.findById(roleId)
                .orElseThrow(()->new EntityNotFoundException("Ce role n'existe pas"));
        role.setName(roleRequest.name());
        role.setUsers(roleRequest.userList());
        this.roleRepository.save(role);
        return this.roleMapperClass.ToRoleResponse(role);
    }

    public void deleteRole(int roleId) {
        this.roleRepository.deleteById(
                this.roleRepository.findById(roleId)
                        .orElseThrow(()->new EntityNotFoundException("Ce role n existe pas!")).getId());
    }
}
