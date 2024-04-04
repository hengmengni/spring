package com.ardb.spring_test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.ardb.spring_test.model.ResponseMessage;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ ProductNotFoundException.class, })
    public ResponseEntity<ResponseMessage> handleProductNotFoundException(ProductNotFoundException ex) {
        final var response = new ResponseMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

    }

    @ExceptionHandler({ CategoryNotFoundException.class })
    public ResponseEntity<ResponseMessage> handleCategoryNotFoundException(CategoryNotFoundException ex) {
        final var response = new ResponseMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler({ ProductAlreadyHaveException.class, })
    public ResponseEntity<ResponseMessage> handleProductAlreadyHave(ProductAlreadyHaveException ex) {
        final var response = new ResponseMessage(ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler({ CategoryAlreadyHaveException.class })
    public ResponseEntity<ResponseMessage> handleCategoryAlreadyHaveException(CategoryAlreadyHaveException ex) {
        final var response = new ResponseMessage(ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler({ FileNotFoundException.class })
    public ResponseEntity<ResponseMessage> handleFileNotFoundException(FileNotFoundException ex) {
        final var response = new ResponseMessage(ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

}