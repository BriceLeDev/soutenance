package com.soutenence.publiciteApp.controller;

import com.soutenence.publiciteApp.ResponseAndRequest.PageResponse;
import com.soutenence.publiciteApp.ResponseAndRequest.PanneauResponse;
import com.soutenence.publiciteApp.ResponseAndRequest.PanneauRquest;
import com.soutenence.publiciteApp.service.PanneauService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("panneau")
@Tag(name="Panneau", description = "Gestion des panneaux")
public class PanneauController {

    private  final PanneauService panneauService;
    public PanneauController(PanneauService panneauService) {
        this.panneauService = panneauService;
    }
    @PostMapping
    public ResponseEntity<Long> savePanneau(@Valid @RequestBody PanneauRquest panneauRquest ) throws IllegalAccessException {

        return  ResponseEntity.ok(panneauService.savePanneau(panneauRquest));
    }



    @GetMapping(path ="{panneau-id}" )
    public ResponseEntity<PanneauResponse> panneauFindById(@PathVariable("{panneau-id}") Long panneauId){
        return ResponseEntity.ok(panneauService.findById(panneauId));
    }

    @PutMapping(path ="/update/{panneau-id}" )
    public ResponseEntity<PanneauResponse> updatePanneauById(
            @PathVariable("panneau-id") Long panneauId,
            @RequestBody @Valid PanneauRquest panneauRquest
            )
    {
        return ResponseEntity.ok(panneauService.updatePanneauById(panneauId,panneauRquest));
    }

    @DeleteMapping(path = "/{panneau-id}")
    public void deletePannau(@PathVariable(name = "panneau-id") Long panneauId){
        this.panneauService.deletePanneau(panneauId);
    }

    @Operation(summary = "Lister tous les panneaux", description = "Cette méthode retourne tous les panneaux disponibles.")
    @GetMapping("/panneaux")
    public ResponseEntity<PageResponse<PanneauResponse>> getAllPanneaux(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ){
        return ResponseEntity.ok(panneauService.getAllPanneaux(page,size));
    }
    @GetMapping("/panneaux-libre")
    public ResponseEntity<PageResponse<PanneauResponse>> getAllPanneauxLibre(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ){
        return ResponseEntity.ok(panneauService.getAllPanneauxLibre(page,size));
    }
    @GetMapping("/panneaux-occuper")
    public ResponseEntity<PageResponse<PanneauResponse>> getAllPanneauxOccuper(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ){
        return ResponseEntity.ok(panneauService.getAllPanneauxOccuper(page,size));
    }
    @GetMapping("/panneaux-libre/boulevard/{boulevard}")
    public ResponseEntity<PageResponse<PanneauResponse>> getAllPanneauxLibreByBoulevard(
            @PathVariable("boulevard")String boulevardId,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ){
        return ResponseEntity.ok(panneauService.getAllPanneauxLibreByBoulevard(boulevardId,page,size));
    }
    @GetMapping("/panneaux-occuper/boulevard/{boulevard}")
    public ResponseEntity<PageResponse<PanneauResponse>> getAllPanneauxOccuperByBoulevard(
            @PathVariable("boulevard")String boulevarName,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ){
        return ResponseEntity.ok(panneauService.getAllPanneauxOccuperByBoulevard(boulevarName,page,size));
    }
    @GetMapping("/boulevard/{boulevard}")
    public ResponseEntity<PageResponse<PanneauResponse>> getAllPanneauxByBoulevard(
            @PathVariable("boulevard")String boulevardId,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ){
        return ResponseEntity.ok(panneauService.getAllPanneauxByBoulevard(boulevardId,page,size));
    }
}
