package com.soutenence.publiciteApp.controller;

import com.soutenence.publiciteApp.ResponseAndRequest.TypePanRequest;
import com.soutenence.publiciteApp.ResponseAndRequest.TypePanResponse;
import com.soutenence.publiciteApp.entity.TypePanneau;
import com.soutenence.publiciteApp.service.TypePanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("type-panneau")
@Tag(name="TypePanneau")
public class TypePanController {

    private final TypePanService typePanService;

    public TypePanController(TypePanService typePanService) {
        this.typePanService = typePanService;
    }

    @PostMapping
    public ResponseEntity<Long> addType(@Valid @RequestBody TypePanRequest typePanRequest) throws IllegalAccessException {
        return ResponseEntity.ok(this.typePanService.addType(typePanRequest));
    }

    @GetMapping("{type-id}")
    public ResponseEntity<TypePanneau> getType(@PathVariable(name = "type-id") Long typeId){
        return ResponseEntity.ok(this.typePanService.getType(typeId));
    }
    @Operation(summary = "Lister tous les type panneaux", description = "Cette méthode retourne tous les types panneaux disponibles.")
    @GetMapping("all-type-panneau")
    public ResponseEntity<List<TypePanResponse>> getAllType(){
        return ResponseEntity.ok(this.typePanService.getAllType());
    }

    @DeleteMapping(path = "/{type-id}")
    public void deleteTypePannau(@PathVariable(name = "type-id") Long typeId){
        this.typePanService.deletePanneau(typeId);
    }


    @PutMapping(path ="/update/{type-id}" )
    public ResponseEntity<TypePanResponse> updatePanneauById(
            @PathVariable("type-id") Long id,
            @RequestBody @Valid TypePanRequest Request
    )
    {
        return ResponseEntity.ok(typePanService.updateTypeById(id,Request));
    }

}
