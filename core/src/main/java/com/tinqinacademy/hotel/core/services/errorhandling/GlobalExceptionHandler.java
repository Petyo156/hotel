package com.tinqinacademy.hotel.core.services.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleException(MethodArgumentNotValidException ex) {

        List<ErrorResponse> errorResponses = ex.getBindingResult().getAllErrors().stream()
                .map(error -> ErrorResponse.builder()
                        .message(error.getDefaultMessage())
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .build())
                .collect(Collectors.toList());

        ErrorWrapper errorWrapper = ErrorWrapper.builder()
                .errorResponseList(errorResponses)
                .date(new Date(System.currentTimeMillis()))
                .build();

        return new ResponseEntity<>(errorWrapper, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleAnotherException(RuntimeException ex) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(ex.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


}
