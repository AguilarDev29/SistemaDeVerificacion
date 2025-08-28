package com.covielloDevs.SistemaDeVerificacion.utils.dataReader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

public class DataReaderFactory {

    public static String getReader(String filePath) throws Exception {
        IDataReader dataReader = null;
        switch (filePath.substring(filePath.lastIndexOf(".") + 1)) {
            case "csv" -> dataReader = new CSVDataReader();
            case "xlsx" -> dataReader = new ExcelDataReader();
            default -> throw new IllegalArgumentException("Tipo de archivo no soportado");
        };
        List<Map<String, Object>> dataList = dataReader.read(filePath);
        return jsonPaser(dataList);
    }

    private static String jsonPaser(List<Map<String, Object>> data) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
    }
}