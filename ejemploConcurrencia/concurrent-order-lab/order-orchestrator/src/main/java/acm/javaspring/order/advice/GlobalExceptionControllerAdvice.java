package acm.javaspring.order.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex){
        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("error:", ex.getMessage());
        messageMap.put("success", false);
        messageMap.put("prueba", "Hola mundo con errores");
        return ResponseEntity.badRequest().body(messageMap);
    }

}
