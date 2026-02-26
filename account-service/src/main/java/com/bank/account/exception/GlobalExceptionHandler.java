package com.bank.account.exception;

import com.bank.account.domain.exception.ObjectNotFoundException;
import com.bank.account.domain.model.dto.ErrorDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;
import java.time.OffsetDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public Mono<ResponseEntity<ErrorDetails>> handleNotFound(ObjectNotFoundException ex) {
        log.error("Resource not found: {}", ex.getMessage());
        ErrorDetails error = buildError("NOT_FOUND", ex.getMessage());
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(error));
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<ErrorDetails>> handleValidationErrors(WebExchangeBindException ex) {
        String details = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        log.error( details);
        ErrorDetails error = buildError("BAD_REQUEST", "Invalid data: " + details);
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorDetails>> handleGeneralError(Exception ex) {
        log.error("Internal server error", ex);
        ErrorDetails error = buildError("INTERNAL_ERROR",ex.getMessage() );
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error));
    }

    private ErrorDetails buildError(String code, String message) {
        ErrorDetails error = new ErrorDetails();
        error.setCode(code);
        error.setMessage(message);
        error.setTimestamp(OffsetDateTime.now());
        return error;
    }
}

