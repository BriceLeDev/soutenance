package com.soutenence.publiciteApp.controller;

import com.soutenence.publiciteApp.ResponseAndRequest.PageResponse;
import com.soutenence.publiciteApp.ResponseAndRequest.UserResponse;
import com.soutenence.publiciteApp.ResponseAndRequest.adminFormRequest;
import com.soutenence.publiciteApp.service.UserService;
import com.soutenence.publiciteApp.validationObjet.RegistrationFormRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "owner")
@RequestMapping("owner")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "user-by-id")
    public UserResponse getTheUserById(@RequestParam(name = "userId") long userId){
        return  this.userService.getUser(userId);
    }

    @GetMapping(path = "/{owner-email}")
    public UserResponse getUserByEmail(@RequestParam(name = "email") String email){
        return this.userService.getUserByEmail(email);
    }

    @CrossOrigin(originPatterns = "http://localhost:4200")
    @PutMapping(path = "/update/{owner-id}")
    public UserResponse updateUser(@PathVariable("owner-id") long userId,@RequestBody @Valid RegistrationFormRequest request){
        return this.userService.updateUser(userId,request);
    }

    @CrossOrigin(originPatterns = "http://localhost:4200")
    @PutMapping(path = "/update/set-fidelisation/{owner-id}")
    public ResponseEntity<?> updateUserFidelisationToFalse(@PathVariable("owner-id") long userId ){
         this.userService.setFidelisation(userId);
         return ResponseEntity.ok().build();
    }


    @CrossOrigin(originPatterns = "http://localhost:4200")
    @PutMapping(path = "/update/remove-fidelisation/{owner-id}")
    public ResponseEntity<?> removeUserFidelisation(@PathVariable("owner-id") long userId ){
         this.userService.setDeFidelisation(userId);
         return ResponseEntity.ok().build();
    }
    @CrossOrigin(originPatterns = "http://localhost:4200")
    @PutMapping(path = "/update/blocked-user/{owner-id}")
    public ResponseEntity<?> updateUserBlocked(@PathVariable("owner-id") long userId ){
         this.userService.blockUser(userId);
         return ResponseEntity.ok().build();
    }
    @CrossOrigin(originPatterns = "http://localhost:4200")
    @PutMapping(path = "/update/de-blocked-user/{owner-id}")
    public ResponseEntity<?> updateUserDeBlocked(@PathVariable("owner-id") long userId ){
         this.userService.deBlockUser(userId);
         return ResponseEntity.ok().build();
    }

    @PostMapping("/add-admin")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @CrossOrigin(originPatterns = "http://localhost:4200")
    public ResponseEntity<?> register(@RequestBody @Valid adminFormRequest request) throws MessagingException {
        userService.addAdmin(request);
        return ResponseEntity.accepted().build();
    }


    @GetMapping("/all-customer")
    @CrossOrigin(originPatterns = "http://localhost:4200")
    public PageResponse<UserResponse> getAllCustomer(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ){
        return  this.userService.getAllUsersCustomer(page,size);
    }
    @GetMapping("/all-customer-abonnement")
    @CrossOrigin(originPatterns = "http://localhost:4200")
    public PageResponse<UserResponse> getAllCustomerWithAbonnementTrue(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ){
        return  this.userService.getAllUsersCustomerAbnment(page,size);
    }

    @GetMapping("/all-customer-no-abonnement")
    @CrossOrigin(originPatterns = "http://localhost:4200")
    public PageResponse<UserResponse> getAllCustomerWithAbonnementFalse(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ){
        return  this.userService.getAllUsersCustomerNoAbnment(page,size);
    }

    @GetMapping("/all-admin")
    @CrossOrigin(originPatterns = "http://localhost:4200")
    public PageResponse<UserResponse> getAllAdmin(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ){
        return  this.userService.getAllUsersAdmin(page,size);
    }

}
