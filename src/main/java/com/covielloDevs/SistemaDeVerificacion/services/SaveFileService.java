package com.covielloDevs.SistemaDeVerificacion.services;

import com.covielloDevs.SistemaDeVerificacion.utils.saveFiles.ISaveFiles;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class SaveFileService {

    private final ISaveFiles saveFiles;

    public SaveFileService(ISaveFiles saveFiles) {
        this.saveFiles = saveFiles;
    }

    public String saveSpreadSheet(MultipartFile file) throws IOException {
        return saveFiles.save(file);
    }
}
