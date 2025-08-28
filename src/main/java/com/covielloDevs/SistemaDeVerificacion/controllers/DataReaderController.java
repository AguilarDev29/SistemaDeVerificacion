package com.covielloDevs.SistemaDeVerificacion.controllers;

import com.covielloDevs.SistemaDeVerificacion.services.SaveFileService;
import com.covielloDevs.SistemaDeVerificacion.utils.dataReader.ExcelDataReader;
import com.covielloDevs.SistemaDeVerificacion.utils.dataReader.IDataReader;
import com.covielloDevs.SistemaDeVerificacion.utils.saveFiles.SaveSpreadSheet;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/data")
public class DataReaderController {

    private final ExcelDataReader excelDataReader;
    private final SaveSpreadSheet saveSpreadSheet;
    public DataReaderController(ExcelDataReader excelDataReader, SaveSpreadSheet saveSpreadSheet) {
        this.excelDataReader = excelDataReader;
        this.saveSpreadSheet = saveSpreadSheet;
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getData(@RequestParam("filePath") String filePath) throws Exception {
        return ResponseEntity.ok(excelDataReader.read(filePath));
    }

    @PostMapping
    public ResponseEntity<String> saveFile(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(saveSpreadSheet.save(file));
    }


}
