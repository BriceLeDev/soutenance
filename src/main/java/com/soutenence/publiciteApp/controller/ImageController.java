package com.soutenence.publiciteApp.controller;

import com.soutenence.publiciteApp.ResponseAndRequest.ImageResponse;
import com.soutenence.publiciteApp.service.ImageService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping( "image")
@Tag(name = "Image")
public class ImageController {


    private  final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }


    //uploder une image
    @PostMapping(value = "ajout-image/{abonnement-id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveImae(@RequestPart("file") MultipartFile file,
                                      @Parameter() //swagger
                                      @PathVariable("abonnement-id") Long abonnementId,
                                      Authentication authentication)
    {
        imageService.saveFile(file,abonnementId);
        return ResponseEntity.accepted().build();

    }

    @GetMapping
    public List<ImageResponse> findAllImages( @RequestParam Long abonnementId){
      return   imageService.findAllImages(abonnementId);

    }

    @GetMapping("get-image-by-abonnement")
    public List<ImageResponse> getImageByAbonnement(@RequestParam("abonnementId") Long abonnementId){
        return this.imageService.getImageByAbonnement(abonnementId);
    }
}
