package com.xe.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xe.model.Conversion;
import com.xe.model.Currency;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class ConversionDAO {

    public List<Conversion> getCurrencies() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        List<Conversion> currencies = objectMapper.readValue(new FileReader("C:\\Users\\andre\\IdeaProjects\\UIAutomationXE\\src\\main\\java\\com\\xe\\data\\conversion.json"), new TypeReference<List<Conversion>>(){});

        return currencies;
    }
}
