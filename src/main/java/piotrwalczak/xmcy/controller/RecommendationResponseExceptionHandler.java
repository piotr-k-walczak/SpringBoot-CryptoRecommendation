package piotrwalczak.xmcy.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import piotrwalczak.xmcy.exception.UnsupportedSymbolException;

@ControllerAdvice
public class RecommendationResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {UnsupportedSymbolException.class})
    protected ResponseEntity<Object> handleUnsupportedSymbolException(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, "", new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, request);
    }
}
