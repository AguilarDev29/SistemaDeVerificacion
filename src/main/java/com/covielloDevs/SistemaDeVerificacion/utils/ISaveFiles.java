package com.covielloDevs.SistemaDeVerificacion.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public interface ISaveFiles {

    default String saveImagen(MultipartFile imagen) throws IOException {

        final String UPLOAD_DIR = "/home/matias/pruebas/images/";

        String contentType = imagen.getContentType();
        if(contentType == null || !contentType.startsWith("image/"))
            throw new RuntimeException("Imagen no valida");

        String nombreImagen = UUID.randomUUID() + "-" + imagen.getOriginalFilename();
        Path rutaArchivo = Paths.get(UPLOAD_DIR, nombreImagen);

        return Files.write(rutaArchivo, imagen.getBytes()).toUri().toString();
    }
}
