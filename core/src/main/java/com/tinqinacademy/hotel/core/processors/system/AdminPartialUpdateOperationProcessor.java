package com.tinqinacademy.hotel.core.processors.system;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import com.tinqinacademy.hotel.api.models.exceptions.Errors;
import com.tinqinacademy.hotel.api.models.operations.system.adminpartialupdate.AdminPartialUpdateInput;
import com.tinqinacademy.hotel.api.models.operations.system.adminpartialupdate.AdminPartialUpdateOperation;
import com.tinqinacademy.hotel.api.models.operations.system.adminpartialupdate.AdminPartialUpdateOutput;
import com.tinqinacademy.hotel.api.models.operations.system.adminreportvisitor.AdminReportVisitorOutput;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@Slf4j
@Service
public class AdminPartialUpdateOperationProcessor extends BaseOperationProcessor implements AdminPartialUpdateOperation {
    private final RoomsRepository roomsRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public AdminPartialUpdateOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper, Validator validator,
                                                RoomsRepository roomsRepository, ObjectMapper objectMapper) {
        super(conversionService, errorMapper, validator);
        this.roomsRepository = roomsRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public Either<Errors, AdminPartialUpdateOutput> process(AdminPartialUpdateInput input) {
        return validateInput(input)
                .flatMap(valid -> getAdminPartialUpdateOutputs(input));
    }

    private Either<Errors, AdminPartialUpdateOutput> getAdminPartialUpdateOutputs(AdminPartialUpdateInput input) {
        return Try.of(() -> {
                    log.info("Start adminPartialUpdate input: {}", input);

                    Optional<Room> optionalRoom = roomsRepository.findById(input.getRoomId());
                    throwIfRoomDoesntExist(optionalRoom);

                    Room room = optionalRoom.get();

                    JsonNode roomNode = objectMapper.valueToTree(room);

                    JsonNode inputNode = objectMapper.valueToTree(input);

                    try {
                        JsonMergePatch patch = JsonMergePatch.fromJson(inputNode);
                        JsonNode patchedNode = patch.apply(roomNode);

                        Room patchedRoom = objectMapper.treeToValue(patchedNode, Room.class);

                        roomsRepository.save(patchedRoom);

                        AdminPartialUpdateOutput output = AdminPartialUpdateOutput.builder()
                                .id(patchedRoom.getId().toString())
                                .build();

                        log.info("End adminPartialUpdate output: {}", output);
                        return output;
                    } catch (JsonPatchException | IOException e) {
                        throw new RuntimeException("Failed to apply patch to room: " + e.getMessage(), e);
                    }
                })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        Case($(instanceOf(IllegalArgumentException.class)),
                                errorMapper.handleError(throwable, HttpStatus.BAD_REQUEST)),
                        Case($(instanceOf(RuntimeException.class)),
                                errorMapper.handleError(throwable, HttpStatus.BAD_REQUEST))
                ));
    }
}
