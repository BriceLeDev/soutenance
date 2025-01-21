package com.soutenence.publiciteApp.UtilitiesFiles;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.rmi.server.LogStream.log;

@Slf4j
public class FilesUtils {
    public static byte[] readFileFromLocation(String fileUrl) {
        if (StringUtils.isAllBlank(fileUrl)){
            return null;
        }

        try {
            Path fileUrlPath = new File(fileUrl).toPath();
           return  Files.readAllBytes(fileUrlPath);
        } catch (IOException e) {
            log("Aucune image " + e);
        }

        return null;
    }
}
