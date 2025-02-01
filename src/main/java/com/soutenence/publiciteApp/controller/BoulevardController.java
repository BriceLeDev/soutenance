package com.soutenence.publiciteApp.controller;

import com.soutenence.publiciteApp.ResponseAndRequest.PageResponse;
import com.soutenence.publiciteApp.service.BoulevardService;
import com.soutenence.publiciteApp.validationObjet.BoulevarRequest;
import com.soutenence.publiciteApp.validationObjet.BoulevardResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("boulevard")
@Tag(name = "Boulevard")
public class BoulevardController {

    private final  BoulevardService boulevardService;

    public BoulevardController(BoulevardService boulevardService) {
        this.boulevardService = boulevardService;
    }

    @PostMapping
    public ResponseEntity<Long> saveBoulevard
            (@Valid @RequestBody
             BoulevarRequest request,
             Authentication connectedUser
            ){
        return ResponseEntity.ok(boulevardService.save(request,connectedUser));
    }

    @GetMapping(path = "/{boulevard-id}")
    public ResponseEntity<BoulevardResponse> boulevardFindById(@PathVariable("boulevard-id") Integer boulevardId){
        return ResponseEntity.ok(boulevardService.findById(boulevardId));
    }

    @PutMapping(path = "/update/{boulevard-id}")
    public ResponseEntity<BoulevardResponse> updateBoulevard( @PathVariable("boulevard-id") Integer boulevardId,@RequestBody @Valid BoulevarRequest request){
        return ResponseEntity.ok(boulevardService.updateBoulevard(boulevardId, request));
    }

    @DeleteMapping(path = "/{boulevard-id}")
    public void deleteBoulevard(@PathVariable("boulevard-id") Integer boulevardId){
        boulevardService.deleteBoulevard(boulevardId);
    }
    @GetMapping("/all-boulevard")
    public ResponseEntity<PageResponse<BoulevardResponse>> boulevardFindAll(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    )
    {
        return ResponseEntity.ok(boulevardService.findAll(page,size));
    }

    @GetMapping("/boulevard-by-panneau")
    public  BoulevardResponse getBoulByPanneau(@RequestParam("panneauId") Long panneauId){
        return this.boulevardService.getBoulByPanneau(panneauId);
    }
}
