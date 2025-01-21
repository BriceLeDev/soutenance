package com.soutenence.publiciteApp.ResponseAndRequest;

import lombok.Builder;

@Builder
public class ImageResponse {

    private byte[] picture;


    public ImageResponse(byte[] picture) {
        this.picture = picture;
    }

    public ImageResponse() {
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }
}
