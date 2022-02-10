package com.cyecize.demo.util.converters;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JsonDeserialize(converter = LocalDateDeserializer.class)
@JsonSerialize(converter = LocalDateSerializer.class)
@JacksonAnnotationsInside
public @interface LocalDateConverter {
}
