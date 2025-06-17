package com.workintech.zoo.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ZooGlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ZooErrorResponse> handleZooException(ZooException e) {
        ZooErrorResponse error = new ZooErrorResponse(
                e.getHttpStatus().value(),
                e.getMessage(),
                System.currentTimeMillis()
        );
        log.error("ZooException: {}", e.getMessage());
        return new ResponseEntity<>(error, e.getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ZooErrorResponse> handleException(Exception e) {
        ZooErrorResponse error = new ZooErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Beklenmeyen bir hata oluştu.",
                System.currentTimeMillis()
        );
        log.error("Unhandled Exception: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
