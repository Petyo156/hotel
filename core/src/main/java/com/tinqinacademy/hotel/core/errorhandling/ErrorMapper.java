package com.tinqinacademy.hotel.core.errorhandling;

import com.tinqinacademy.hotel.api.models.base.OperationOutput;
import com.tinqinacademy.hotel.api.models.exceptions.ErrorResponse;
import com.tinqinacademy.hotel.api.models.exceptions.ErrorWrapper;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ErrorMapper {
    public ErrorWrapper handleError(Throwable ex, HttpStatus httpStatus){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(ex.getMessage())
                .httpStatus(httpStatus)
                .build();

        return ErrorWrapper.builder()
                .errorResponseList(List.of(errorResponse))
                .date(new Date(System.currentTimeMillis()))
                .build();
    }
}
