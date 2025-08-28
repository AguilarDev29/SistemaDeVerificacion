package com.covielloDevs.SistemaDeVerificacion.utils.dataReader;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ExcelDataReader implements IDataReader{
    @Override
    public List<Map<String, Object>> read(String filePath) throws Exception {
        List<Map<String, Object>> data = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(new File(filePath));
             Workbook workbook = WorkbookFactory.create(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0);
            List<String> headers = new ArrayList<>();

            headerRow.forEach(cell -> headers.add(cell.getStringCellValue()));

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                Map<String, Object> map = new HashMap<>();
                for (int j = 0; j < headers.size(); j++) {
                    Cell cell = row.getCell(j);
                    map.put(headers.get(j), cell != null ? cell.toString() : null);
                }
                data.add(map);
            }
        }
        return data;
    }
}
