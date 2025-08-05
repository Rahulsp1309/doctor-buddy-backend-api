package com.example.security.exception;

import com.example.security.dto.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({PdfFileNotFoundException.class})
    public ResponseEntity<Object> handleStudentNotFoundException(PdfFileNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex){
        ErrorMessage errorMessage = ErrorMessage.builder()
                .message(ex.getMessage())
                .statusCode(String.valueOf(HttpStatus.NOT_FOUND))
                .build();
        return new  ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);

    }
}
