package com.tinqinacademy.hotel.api.models.exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ErrorResponse implements Errors{
    private String message;
    private HttpStatus httpStatus;
}
