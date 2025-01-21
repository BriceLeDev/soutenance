package com.soutenence.publiciteApp.Mapper;

import com.soutenence.publiciteApp.ResponseAndRequest.ImageResponse;
import com.soutenence.publiciteApp.UtilitiesFiles.FilesUtils;
import com.soutenence.publiciteApp.entity.Image;
import org.springframework.stereotype.Component;

@Component
public class ImageMapperClass {

    public ImageResponse toImageResponse(Image image){

        return ImageResponse.builder()
                .picture(FilesUtils.readFileFromLocation(image.getNomImage()))
                .build();
    }
}
