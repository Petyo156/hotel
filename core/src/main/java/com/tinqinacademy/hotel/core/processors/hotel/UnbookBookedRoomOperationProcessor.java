package com.tinqinacademy.hotel.core.processors.hotel;

import com.tinqinacademy.hotel.api.models.exceptions.Errors;
import com.tinqinacademy.hotel.api.models.operations.hotel.unbookbookedroom.UnbookBookedRoomInput;
import com.tinqinacademy.hotel.api.models.operations.hotel.unbookbookedroom.UnbookBookedRoomOperation;
import com.tinqinacademy.hotel.api.models.operations.hotel.unbookbookedroom.UnbookBookedRoomOutput;
import com.tinqinacademy.hotel.core.errorhandling.ErrorMapper;
import com.tinqinacademy.hotel.core.processors.BaseOperationProcessor;
import com.tinqinacademy.hotel.persistance.entities.Reservation;
import com.tinqinacademy.hotel.persistance.repositories.ReservationsRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import jakarta.validation.Validator;

import java.util.Optional;
import java.util.UUID;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@Slf4j
@Service
public class UnbookBookedRoomOperationProcessor extends BaseOperationProcessor implements UnbookBookedRoomOperation {
    private final ReservationsRepository reservationsRepository;

    @Autowired
    public UnbookBookedRoomOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper, Validator validator, ReservationsRepository reservationsRepository) {
        super(conversionService, errorMapper, validator);
        this.reservationsRepository = reservationsRepository;
    }

    @Override
    public Either<Errors, UnbookBookedRoomOutput> process(UnbookBookedRoomInput input) {
        return validateInput(input)
                .flatMap(valid -> getUnbookBookedRoomOutputs(input));
    }

    private Either<Errors, UnbookBookedRoomOutput> getUnbookBookedRoomOutputs(UnbookBookedRoomInput input) {
        return Try.of(() -> {
                    log.info("Start unbookBookedRoom input: {}", input);

                    Optional<Reservation> reservationOptional = reservationsRepository.findById(UUID.fromString(input.getBookingId()));
                    throwIfReservationDoesntExist(reservationOptional);

                    reservationsRepository.delete(reservationOptional.get());

                    UnbookBookedRoomOutput output = UnbookBookedRoomOutput.builder()
                            .build();

                    log.info("End unbookBookedRoom output: {}", output);
                    return output;
                })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        Case($(instanceOf(IllegalArgumentException.class)),
                                errorMapper.handleError(throwable, HttpStatus.BAD_REQUEST))
                ));
    }
}
