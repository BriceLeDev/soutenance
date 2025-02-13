package com.soutenence.publiciteApp.controller;

import com.soutenence.publiciteApp.ResponseAndRequest.LigneAbonnementResponse;
import com.soutenence.publiciteApp.service.LigneAbonnementService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("line-abonnement")
@RestController
@Tag(name="Ligne-Abonnment")
public class LigneAbonnmentController {

    private final LigneAbonnementService ligneAbonnementService;

    public LigneAbonnmentController(LigneAbonnementService ligneAbonnementService) {
        this.ligneAbonnementService = ligneAbonnementService;
    }

    @GetMapping("/all-by-user")
    public List<LigneAbonnementResponse> getAllLigneAbn(Long abonnementId){
        return this.ligneAbonnementService.getAllLineByAbonnement(abonnementId);
    }
    @GetMapping("/all-line")
    public List<LigneAbonnementResponse> getAllLigneAbn(){
        return this.ligneAbonnementService.getAllLine();
    }
}
