package com.xe.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xe.model.Currency;
import jdk.nashorn.internal.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class CurrencyDAO {

    public List<Currency> getCurrencies() throws FileNotFoundException, JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        List<Currency> currencies = objectMapper.readValue(new FileReader("currency.json").toString(), new TypeReference<List<Currency>>(){});

        return currencies;
    }
}
