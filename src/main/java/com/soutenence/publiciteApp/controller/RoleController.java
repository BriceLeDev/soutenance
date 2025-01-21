package com.soutenence.publiciteApp.controller;

import com.soutenence.publiciteApp.ResponseAndRequest.RoleRequest;
import com.soutenence.publiciteApp.ResponseAndRequest.RoleRespons;
import com.soutenence.publiciteApp.service.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "role")
@RequestMapping("role")
public class RoleController {
    private final RoleService roleService;


    public RoleController(RoleService roleService) {
        this.roleService = roleService;

    }

    @PostMapping
    public ResponseEntity<Integer> addRole(@RequestBody @Valid RoleRequest roleRequest) {
        return ResponseEntity.ok(roleService.addRole(roleRequest));
    }
    @GetMapping(path = "/add-role/{role-id}")
    public RoleRespons getRole(@PathVariable("role-id") int roleId){
        return this.roleService.getRole(roleId);
    }
    @DeleteMapping(path = "/add-role/{role-id}")
    public void deleteRole(@PathVariable("role-id") int roleId){
         this.roleService.deleteRole(roleId);
    }
    @GetMapping(path = "/update-role/{role-id}")
    public RoleRespons updateRole(@PathVariable("role-id") int roleId, @RequestBody @Valid RoleRequest roleRequest){
        return this.roleService.update(roleId,roleRequest);
    }
}
