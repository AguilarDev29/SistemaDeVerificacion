package com.covielloDevs.SistemaDeVerificacion.utils.saveFiles;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.UUID;

@Component
public class SaveSpreadSheet implements ISaveFiles{
    @Override
    public String save(MultipartFile file) throws IOException {
        final String UPLOAD_DIR = "/home/matias/pruebas/spreadsheets/";

        String contentType = file.getContentType();
        String originalName = file.getOriginalFilename();
        if(originalName == null) throw new RuntimeException("Archivo sin nombre");

        if(!(originalName.endsWith(".csv") || originalName.endsWith(".xls") || originalName.endsWith(".xlsx")))
            throw new RemoteException("Archivo no valido, solo se permite CSV o excel");

        String nombreArchivo = UUID.randomUUID() + "-" + originalName;
        Path rutaArchivo = Paths.get(UPLOAD_DIR, nombreArchivo);

        Files.write(rutaArchivo, file.getBytes());

        return rutaArchivo.toString();
    }
}
