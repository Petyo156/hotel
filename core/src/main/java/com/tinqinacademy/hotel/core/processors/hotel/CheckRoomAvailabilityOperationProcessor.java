package com.tinqinacademy.hotel.core.processors.hotel;

import com.tinqinacademy.hotel.api.models.exceptions.Errors;
import com.tinqinacademy.hotel.api.models.operations.hotel.checkroom.CheckRoomAvailabilityInput;
import com.tinqinacademy.hotel.api.models.operations.hotel.checkroom.CheckRoomAvailabilityOperation;
import com.tinqinacademy.hotel.api.models.operations.hotel.checkroom.CheckRoomAvailabilityOutput;
import com.tinqinacademy.hotel.core.errorhandling.ErrorMapper;
import com.tinqinacademy.hotel.core.processors.BaseOperationProcessor;
import com.tinqinacademy.hotel.persistance.entities.Room;
import com.tinqinacademy.hotel.persistance.repositories.RoomsRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import jakarta.validation.Validator;

import java.util.List;
import java.util.UUID;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@Slf4j
@Service
public class CheckRoomAvailabilityOperationProcessor extends BaseOperationProcessor implements CheckRoomAvailabilityOperation {

    private final RoomsRepository roomsRepository;
    private final ErrorMapper errorMapper;

    @Autowired
    public CheckRoomAvailabilityOperationProcessor(RoomsRepository roomsRepository, ErrorMapper errorMapper,
                                                   Validator validator, ConversionService conversionService) {
        super(conversionService, errorMapper, validator);
        this.roomsRepository = roomsRepository;
        this.errorMapper = errorMapper;
    }

    @Override
    public Either<Errors, CheckRoomAvailabilityOutput> process(CheckRoomAvailabilityInput input) {
        return validateInput(input)
                .flatMap(valid -> getCheckRoomAvailabilityOutputs(input));
    }

    private Either<Errors, CheckRoomAvailabilityOutput> getCheckRoomAvailabilityOutputs(CheckRoomAvailabilityInput input) {
        return Try.of(() -> {
                    log.info("Start checkRoomAvailability input: {}", input);

                    List<Room> rooms = roomsRepository.findAvailableRooms(input.getStartDate(), input.getEndDate());

                    List<UUID> availableRoomIdsList = rooms.stream()
                            .map(Room::getId)
                            .toList();

                    CheckRoomAvailabilityOutput output = CheckRoomAvailabilityOutput.builder()
                            .ids(availableRoomIdsList)
                            .build();

                    log.info("End checkRoomAvailability output: {}", output);
                    return output;
                })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        Case($(instanceOf(IllegalArgumentException.class)),
                                errorMapper.handleError(throwable, HttpStatus.BAD_REQUEST))
                ));
    }
}
