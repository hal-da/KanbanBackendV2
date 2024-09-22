package at.technikum.springrestbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleEmailExistsException(Exception e) {
        Map<String, String> errorResponse = new HashMap<>();

        errorResponse.put("error", e.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}