package com.example.springbootdatajpa.utils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateDeserializer extends JsonDeserializer<Date> {
    private final SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MMM-yyyy");

    @Override
    public Date deserialize(JsonParser jsonParser,
                            DeserializationContext deserializationContext) throws IOException, JacksonException {
        String value = jsonParser.getValueAsString();
        try {
            return inputFormat.parse(value);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
