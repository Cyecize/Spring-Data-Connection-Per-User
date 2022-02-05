package com.cyecize.demo.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;
import static org.springframework.web.servlet.HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ResponseEntity<BindingErrorResponse> handleValidationException(WebRequest webRequest,
                                                                          MethodArgumentNotValidException ex) {
        final List<FieldErrorViewModel> fieldErrors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fe -> new FieldErrorViewModel(
                        fe.getDefaultMessage(),
                        fe.getField(),
                        fe.getRejectedValue(),
                        fe.isBindingFailure(),
                        fe.getCode()
                ))
                .collect(Collectors.toList());

        return new ResponseEntity<>(
                new BindingErrorResponse(
                        this.getPath(webRequest),
                        HttpStatus.NOT_ACCEPTABLE,
                        ex.getBindingResult().getTarget().getClass(),
                        fieldErrors
                ),
                HttpStatus.NOT_ACCEPTABLE
        );
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(WebRequest webRequest, ApiException exception) {
        final HttpStatus status = exception.getStatus();
        final String message;
        if (exception.getMessage() != null) {
            message = exception.getMessage();
        } else {
            message = "Message not provided!";
        }

        return new ResponseEntity<>(new ErrorResponse(
                this.getPath(webRequest),
                status,
                message
        ), status);
    }

    private String getPath(WebRequest webRequest) {
        return webRequest.getAttribute(PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE, SCOPE_REQUEST) + "";
    }
}
