package com.tinqinacademy.hotel.api.models.base;

import io.vavr.control.Either;
import org.springframework.validation.Errors;

public interface OperationProcessor<S extends OperationInput, T extends OperationOutput> {
    Either<Errors, T> process(S input);
}
