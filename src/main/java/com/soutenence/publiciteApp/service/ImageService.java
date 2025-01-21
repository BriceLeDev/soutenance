package com.soutenence.publiciteApp.service;

import com.soutenence.publiciteApp.Mapper.ImageMapperClass;
import com.soutenence.publiciteApp.ResponseAndRequest.ImageResponse;
import com.soutenence.publiciteApp.entity.Abonnement;
import com.soutenence.publiciteApp.entity.Image;
import com.soutenence.publiciteApp.repository.AbonnementRepositorie;
import com.soutenence.publiciteApp.repository.ImageRepositorie;
import com.soutenence.publiciteApp.specification.ImageSpecification;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.rmi.server.LogStream.log;

@Slf4j
@Service
public class ImageService {
    @Value("${application.file.upload.picture-output-path}")
   private String fileUploadPath;

    private final AbonnementRepositorie abonnementRepositorie ;
    private final ImageRepositorie imageRepositorie;
    private final ImageMapperClass imageMapperClass;



    public ImageService(AbonnementRepositorie abonnementRepositorie, ImageRepositorie imageRepositorie, ImageMapperClass imageMapperClass) {
        this.abonnementRepositorie = abonnementRepositorie;
        this.imageRepositorie = imageRepositorie;
        this.imageMapperClass = imageMapperClass;
    }

    public void saveFile(@NotNull MultipartFile file, @NotNull Integer abonnementId) {

        Abonnement abonnement = abonnementRepositorie.findById(abonnementId)
                .orElseThrow(()-> new EntityNotFoundException("Cet Abonnement n existe pas"));

        Image image = new Image();
        image.setNomImage(uploadPicture(file,abonnementId));
        image.setAbonnement(abonnement);
        imageRepositorie.save(image);
    }


    public String uploadPicture(MultipartFile file, @NotNull Integer abonnementId) {

        final String fileUploaderSubPath = "abonnement" + File.separator + abonnementId;

        return uploadFile(fileUploaderSubPath,file);
    }

    private String uploadFile(String fileUploaderSubPath, MultipartFile sourceFile) {

         final String finalUploadPath = fileUploadPath + File.separator + fileUploaderSubPath;

        // Crée le répertoire cible si nécessaire avec ce peth finalUploadPath
         File file1 = new File(finalUploadPath);
         if (!file1.exists()){
             boolean folderCreated= file1.mkdirs();
             if (!folderCreated){
                 log("Ce fichier n existe pas");
                 return null;
             }
         }
         //***************
        // Crée le répertoire cible si nécessaire avec ce peth finalUploadPath(second option)

       /* try {
            if (!Files.exists(finalPath)) {
                Files.createDirectories(finalPath);
            }
        } catch (IOException e) {
            log.error("Erreur lors de la création des répertoires pour le chemin : {}", finalUploadPath, e);
            return null;
        }*/
         //****************


        // Obtient l'extension du fichier source
        final  String fileExtension = getFileExtention(sourceFile.getOriginalFilename());
        if (fileExtension == null) {
            throw new RuntimeException("impossible de déterminer l'extension du fichier pour {}");
            //log.error("Impossible de déterminer l'extension du fichier pour : {}", sourceFile.getOriginalFilename());

        }

         String targetFilePath = finalUploadPath + File.separator + System.currentTimeMillis() + "."+fileExtension;
        Path targetPath = Paths.get(targetFilePath);

        try {
            Files.write(targetPath,sourceFile.getBytes());
            return targetFilePath;
        } catch (IOException e) {
            throw new RuntimeException( "image non enrégistrer"+e);
        }

    }

    private String getFileExtention(String filename) {

        if (filename==null || filename.isEmpty()) {
            return null;
        }
        //extraire l extension du fichier(jpeg,jpg..)
        int lastDotIndex = filename.lastIndexOf(".");
        if(lastDotIndex==-1){
            return null;
        }

        return filename.substring(lastDotIndex + 1).toLowerCase();
    }

    public List<ImageResponse> findAllImages(int abonnementId) {

        Abonnement abonnement = abonnementRepositorie.findById(abonnementId)
                .orElseThrow(()-> new EntityNotFoundException("Cet Abonnement n existe pas"));

        List<Image> images = imageRepositorie.findAll(ImageSpecification.byAbonnement(abonnementId));
        List<ImageResponse> imageResponses = images.stream()
                .map(imageMapperClass::toImageResponse)
                .toList();
        return imageResponses;
    }
}
