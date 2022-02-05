package com.cyecize.demo.error;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
public class BindingErrorResponse extends ErrorResponse {

    private final List<FieldErrorViewModel> fieldErrors;

    public BindingErrorResponse(String path, HttpStatus status, Class<?> bindingModelType,
                                List<FieldErrorViewModel> fieldErrors) {
        super(path, status, String.format("Error validating %s!", bindingModelType.getSimpleName()));
        this.fieldErrors = fieldErrors;
    }
}
