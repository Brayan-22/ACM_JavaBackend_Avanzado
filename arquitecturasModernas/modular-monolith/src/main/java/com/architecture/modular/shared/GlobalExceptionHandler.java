package com.architecture.modular.shared;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {
        // En un entorno real se harían excepciones personalizadas (ej. StockInsufficientException)
        // Aquí capturamos los errores de dominio para devolver un mensaje amigable al cliente
        return ResponseEntity.badRequest().body(Map.of(
                "error", true,
                "mensaje", ex.getMessage()
        ));
    }
}
