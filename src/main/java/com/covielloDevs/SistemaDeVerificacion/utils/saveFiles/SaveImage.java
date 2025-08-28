package com.covielloDevs.SistemaDeVerificacion.utils.saveFiles;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class SaveImage implements ISaveFiles{
    @Override
    public String save(MultipartFile image) throws IOException {
        final String UPLOAD_DIR = "/home/matias/pruebas/images/";

        String contentType = image.getContentType();
        if(contentType == null || !contentType.startsWith("image/"))
            throw new RuntimeException("Imagen no valida");

        String nombreImagen = UUID.randomUUID() + "-" + image.getOriginalFilename();
        Path rutaArchivo = Paths.get(UPLOAD_DIR, nombreImagen);

        return Files.write(rutaArchivo, image.getBytes()).toUri().toString();
    }
}
