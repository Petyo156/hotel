package com.tinqinacademy.hotel.core.processors.system;

import com.tinqinacademy.hotel.api.models.exceptions.Errors;
import com.tinqinacademy.hotel.api.models.operations.system.admincreateroom.AdminCreateRoomInput;
import com.tinqinacademy.hotel.api.models.operations.system.admincreateroom.AdminCreateRoomOperation;
import com.tinqinacademy.hotel.api.models.operations.system.admincreateroom.AdminCreateRoomOutput;
import com.tinqinacademy.hotel.core.errorhandling.ErrorMapper;
import com.tinqinacademy.hotel.core.processors.BaseOperationProcessor;
import com.tinqinacademy.hotel.persistance.entities.Bed;
import com.tinqinacademy.hotel.persistance.entities.Room;
import com.tinqinacademy.hotel.persistance.more.BathroomType;
import com.tinqinacademy.hotel.persistance.more.BedSize;
import com.tinqinacademy.hotel.persistance.repositories.BedsRepository;
import com.tinqinacademy.hotel.persistance.repositories.RoomsRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.List;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@Slf4j
@Service
public class AdminCreateRoomOperationProcessor extends BaseOperationProcessor implements AdminCreateRoomOperation {
    private final BedsRepository bedsRepository;
    private final RoomsRepository roomsRepository;

    @Autowired
    public AdminCreateRoomOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper, Validator validator,
                                             BedsRepository bedsRepository, RoomsRepository roomsRepository) {
        super(conversionService, errorMapper, validator);
        this.bedsRepository = bedsRepository;
        this.roomsRepository = roomsRepository;
    }

    @Override
    public Either<Errors, AdminCreateRoomOutput> process(AdminCreateRoomInput input) {
        return Try.of(() -> {
                    log.info("Start adminCreateRoom input: {}", input);

                    List<BedSize> beds = convertBedsStringsFromInputToBedSize(input.getBedSizes());
                    List<Bed> bedList = bedsRepository.findAllByBedSizeIn(beds);

                    throwIfBathroomTypeDoesntExist(input);

                    Room room = conversionService.convert(input, Room.RoomBuilder.class)
                            .beds(bedList)
                            .build();

                    roomsRepository.save(room);

                    AdminCreateRoomOutput output = AdminCreateRoomOutput.builder()
                            .id(room.getId())
                            .build();

                    log.info("End adminCreateRoom output: {}", output);
                    return output;
                })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        Case($(instanceOf(IllegalArgumentException.class)),
                                errorMapper.handleError(throwable, HttpStatus.BAD_REQUEST))
                ));
    }
}
