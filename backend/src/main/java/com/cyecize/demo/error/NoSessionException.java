package com.cyecize.demo.error;

import org.springframework.http.HttpStatus;

public class NoSessionException extends ApiException {
    public NoSessionException() {
        super("No Session!", HttpStatus.FAILED_DEPENDENCY);
    }
}
