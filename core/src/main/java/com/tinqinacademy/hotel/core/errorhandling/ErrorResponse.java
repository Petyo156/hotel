package com.tinqinacademy.hotel.core.errorhandling;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ErrorResponse {
    private String message;
    private HttpStatus httpStatus;
}
