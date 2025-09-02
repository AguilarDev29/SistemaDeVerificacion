package com.covielloDevs.SistemaDeVerificacion.services;

import com.covielloDevs.SistemaDeVerificacion.utils.dataReader.DataReaderFactory;
import org.springframework.stereotype.Service;

@Service
public class DataReaderService {

    public String readData(String filePath) throws Exception {
        return DataReaderFactory.getReader(filePath);
    }
}
