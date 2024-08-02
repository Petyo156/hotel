package com.tinqinacademy.hotel.core.processors.system;

import com.tinqinacademy.hotel.api.models.exceptions.Errors;
import com.tinqinacademy.hotel.api.models.operations.system.deleteroom.DeleteRoomInput;
import com.tinqinacademy.hotel.api.models.operations.system.deleteroom.DeleteRoomOperation;
import com.tinqinacademy.hotel.api.models.operations.system.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.core.errorhandling.ErrorMapper;
import com.tinqinacademy.hotel.core.processors.BaseOperationProcessor;
import com.tinqinacademy.hotel.persistance.entities.Room;
import com.tinqinacademy.hotel.persistance.repositories.RoomsRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
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
public class DeleteRoomOperationProcessor extends BaseOperationProcessor implements DeleteRoomOperation {
    private final RoomsRepository roomsRepository;

    public DeleteRoomOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper, Validator validator,
                                        RoomsRepository roomsRepository) {
        super(conversionService, errorMapper, validator);
        this.roomsRepository = roomsRepository;
    }

    @Override
    public Either<Errors, DeleteRoomOutput> process(DeleteRoomInput input) {
        return validateInput(input)
                .flatMap(valid -> getDeleteRoomOutputs(input));
    }

    private Either<Errors, DeleteRoomOutput> getDeleteRoomOutputs(DeleteRoomInput input) {
        return Try.of(() -> {
                    log.info("Start deleteRoom input: {}", input);

                    Optional<Room> roomsOptional = roomsRepository.findById(UUID.fromString(input.getId()));
                    throwIfRoomDoesntExist(roomsOptional);

                    roomsRepository.delete(roomsOptional.get());

                    DeleteRoomOutput output = DeleteRoomOutput.builder()
                            .build();

                    log.info("End deleteRoom output: {}", output);
                    return output;
                })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        Case($(instanceOf(IllegalArgumentException.class)),
                                errorMapper.handleError(throwable, HttpStatus.BAD_REQUEST))
                ));
    }
}
