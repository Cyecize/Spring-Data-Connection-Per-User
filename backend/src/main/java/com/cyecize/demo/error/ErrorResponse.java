package com.cyecize.demo.error;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
public class ErrorResponse {

    private final Date timestamp;

    private final String path;

    private final Integer status;

    private final String error;

    private final String message;

    public ErrorResponse(String path, Integer status, String error, String message) {
        this.timestamp = new Date();
        this.path = path;
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public ErrorResponse(String path, HttpStatus httpStatus, String message) {
        this(path, httpStatus.value(), httpStatus.getReasonPhrase(), message);
    }
}
