package com.shopx2.product.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        HashMap<String, String> hashMap = new HashMap<>();

        List<ObjectError> objectErrors = ex.getBindingResult().getAllErrors();
        objectErrors
            .forEach(
                objectError -> {
                    String fieldError = ((FieldError) objectError).getField();
                    String message = objectError.getDefaultMessage();
                    hashMap.put(fieldError, message);
                });

        return new ResponseEntity<>(hashMap, HttpStatus.BAD_REQUEST);
    }
}
