package com.covielloDevs.SistemaDeVerificacion.utils.dataReader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVDataReader implements IDataReader{

    @Override
    public List<Map<String, Object>> read(String filePath) throws Exception {
        List<Map<String, Object>> data = new ArrayList<>();

        try(FileReader reader = new FileReader(filePath)){
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader()
                                                            .withAllowMissingColumnNames()
                                                            .parse(reader);
            records.forEach(record -> {
                Map<String, Object> row = new HashMap<>();
                record.toMap().keySet().
                        forEach(header -> row.put(header, record.get(header)));
                data.add(row);
            });

        }
        return data;
    }
}
