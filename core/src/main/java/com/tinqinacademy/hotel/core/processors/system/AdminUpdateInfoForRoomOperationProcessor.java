package com.tinqinacademy.hotel.core.processors.system;

import com.tinqinacademy.hotel.api.models.exceptions.Errors;
import com.tinqinacademy.hotel.api.models.operations.system.adminupdateinfoforroom.AdminUpdateInfoForRoomInput;
import com.tinqinacademy.hotel.api.models.operations.system.adminupdateinfoforroom.AdminUpdateInfoForRoomOperation;
import com.tinqinacademy.hotel.api.models.operations.system.adminupdateinfoforroom.AdminUpdateInfoForRoomOutput;
import com.tinqinacademy.hotel.core.errorhandling.ErrorMapper;
import com.tinqinacademy.hotel.core.processors.BaseOperationProcessor;
import com.tinqinacademy.hotel.persistance.entities.Bed;
import com.tinqinacademy.hotel.persistance.entities.Room;
import com.tinqinacademy.hotel.persistance.more.BedSize;
import com.tinqinacademy.hotel.persistance.repositories.BedsRepository;
import com.tinqinacademy.hotel.persistance.repositories.RoomsRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@Service
@Slf4j
public class AdminUpdateInfoForRoomOperationProcessor extends BaseOperationProcessor implements AdminUpdateInfoForRoomOperation {
    private final RoomsRepository roomsRepository;
    private final BedsRepository bedsRepository;

    public AdminUpdateInfoForRoomOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper, Validator validator,
                                                    RoomsRepository roomsRepository, BedsRepository bedsRepository) {
        super(conversionService, errorMapper, validator);
        this.roomsRepository = roomsRepository;
        this.bedsRepository = bedsRepository;
    }

    @Override
    public Either<Errors, AdminUpdateInfoForRoomOutput> process(AdminUpdateInfoForRoomInput input) {
        return Try.of(() -> {
                    log.info("Start adminUpdateInfoForRoom input: {}", input);

                    Optional<Room> roomsOptional = roomsRepository.findById(UUID.fromString(input.getId()));
                    throwIfRoomDoesntExist(roomsOptional);

                    List<BedSize> beds = convertBedsStringsFromInputToBedSize(input.getBedSizes());
                    List<Bed> bedList = bedsRepository.findAllByBedSizeIn(beds);

                    Room room = conversionService.convert(input, Room.RoomBuilder.class)
                            .beds(bedList)
                            .build();

                    roomsRepository.save(room);

                    AdminUpdateInfoForRoomOutput output = AdminUpdateInfoForRoomOutput.builder()
                            .id(room.getId())
                            .build();

                    log.info("End adminUpdateInfoForRoom output: {}", output);
                    return output;
                })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        Case($(instanceOf(IllegalArgumentException.class)),
                                errorMapper.handleError(throwable, HttpStatus.BAD_REQUEST))
                ));
    }
}
