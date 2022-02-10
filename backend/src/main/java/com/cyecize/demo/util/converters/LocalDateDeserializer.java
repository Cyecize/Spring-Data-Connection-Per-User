package com.cyecize.demo.util.converters;

import com.cyecize.demo.constants.General;
import com.fasterxml.jackson.databind.util.StdConverter;
import org.springframework.boot.jackson.JsonComponent;

import java.time.LocalDateTime;

@JsonComponent
public class LocalDateDeserializer extends StdConverter<String, LocalDateTime> {

    @Override
    public LocalDateTime convert(String source) {
        try {
            return LocalDateTime.parse(source, General.DATE_TIME_FORMATTER);
        } catch (Exception ignored) {
        }

        return null;
    }
}
