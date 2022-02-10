package com.cyecize.demo.util.converters;

import com.cyecize.demo.constants.General;
import com.fasterxml.jackson.databind.util.StdConverter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LocalDateSerializer extends StdConverter<LocalDateTime, String> {
    @Override
    public String convert(LocalDateTime value) {
        return value.format(General.DATE_TIME_FORMATTER);
    }
}
