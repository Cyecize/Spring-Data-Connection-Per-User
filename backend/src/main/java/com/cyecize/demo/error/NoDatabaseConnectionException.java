package com.cyecize.demo.error;

import org.springframework.http.HttpStatus;

public class NoDatabaseConnectionException extends ApiException {
    public NoDatabaseConnectionException() {
        super("No Connection!", HttpStatus.FAILED_DEPENDENCY);
    }
}
