package com.soutenence.publiciteApp.controller;

import com.soutenence.publiciteApp.ResponseAndRequest.AbonnementRequest;
import com.soutenence.publiciteApp.ResponseAndRequest.AbonnementResponse;
import com.soutenence.publiciteApp.ResponseAndRequest.PageResponse;
import com.soutenence.publiciteApp.service.AbonnementService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("abonnement")
@Tag(name = "Abonnement")
public class AbonnementController {

    private final AbonnementService abonnementService;

    public AbonnementController(AbonnementService abonnementService) {
        this.abonnementService = abonnementService;
    }

    @PostMapping
    public ResponseEntity<Long> doAbonnement(@Valid @RequestBody AbonnementRequest abonnementRequest, Authentication authentication){

        return ResponseEntity.ok( abonnementService.saveAbonnement(abonnementRequest,authentication));
    }


    @GetMapping(path = "{abonnement-id}")
    public ResponseEntity<AbonnementResponse>getAbonnementById(@PathVariable("abonnement-id") Long abonnementId){
        return ResponseEntity.ok(abonnementService.getAbonnementById(abonnementId));

    }

    @GetMapping(path = "/by-user/{user-id}")
    public PageResponse<AbonnementResponse>getAbonnementByOwner(
             @PathVariable("user-id") Long userId,
             @RequestParam(name = "page", defaultValue = "0", required = false) int page,
             @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ){
        return abonnementService.getAbonnementByOwner(page,size,userId);

    }

    @GetMapping("/all-abonnements")
    public PageResponse<AbonnementResponse> getAllAbonnement(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ){
        return this.abonnementService.getAllAbonnement(page,size);
    }

    @GetMapping("/expired-abonnements")
    public PageResponse<AbonnementResponse> getAllAbonnementExpired(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ){
        return this.abonnementService.getAllAbonnementExpired(page,size);
    }

    @GetMapping("/expired-abonnements-between-dates")
    public PageResponse<AbonnementResponse> getAllAbonnementExpiredBetween2Date(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ){
        return this.abonnementService.getAllAbonnementExpired(page,size);
    }

    @GetMapping("/expired-abonnements/{id}")
    public PageResponse<AbonnementResponse> getAllAbonnementExpiredByUser(
            @PathVariable(name = "id") int id,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ){
        return this.abonnementService.getAllAbonnementExpiredById(id,page,size);
    }

    @GetMapping("/abonnements-become-one-week")
    public PageResponse<AbonnementResponse> getAllBecomeAbonnement(
            @PathVariable(name = "id") int id,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ){
        return this.abonnementService.getAllBecomeAbonnement(page,size);
    }
    @GetMapping("/abonnements-coming-soon")
    public PageResponse<AbonnementResponse> getAllAbonnementComingSoon(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ){
        return this.abonnementService.getAllAbonnementComingSoon(page,size);
    }
    @GetMapping("/abonnements-between-two-date")
    public PageResponse<AbonnementResponse> getAllAbonnementBetween2Date(
            @PathVariable(name = "id") LocalDate date1,
            @PathVariable(name = "id") LocalDate date2,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ){
        return this.abonnementService.getAllAbonnementBetween2Date(date1,date2,page,size);
    }
    @GetMapping("/abonnements-by-date")
    public PageResponse<AbonnementResponse> getAllAbonnementByDate(
            @PathVariable(name = "id") LocalDate date,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ){
        return this.abonnementService.getAllAbonnementByDateFilter(date,page,size);
    }

    @PutMapping("/validate")
    public void validate(@RequestParam("abonnementId") Long abonnementId){
        this.abonnementService.validateAbonnement(abonnementId);
    }
    @PutMapping("/invalidate")
    public void invalidate(@RequestParam("abonnementId") Long abonnementId){
        this.abonnementService.invalidateAbonnement(abonnementId);
    }

}
