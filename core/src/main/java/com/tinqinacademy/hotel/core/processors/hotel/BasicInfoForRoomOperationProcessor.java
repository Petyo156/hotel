package com.tinqinacademy.hotel.core.processors.hotel;

import com.tinqinacademy.hotel.api.models.exceptions.Errors;
import com.tinqinacademy.hotel.api.models.operations.hotel.basicinfo.BasicInfoForRoomInput;
import com.tinqinacademy.hotel.api.models.operations.hotel.basicinfo.BasicInfoForRoomOperation;
import com.tinqinacademy.hotel.api.models.operations.hotel.basicinfo.BasicInfoForRoomOutput;
import com.tinqinacademy.hotel.core.errorhandling.ErrorMapper;
import com.tinqinacademy.hotel.core.processors.BaseOperationProcessor;
import com.tinqinacademy.hotel.persistance.entities.Reservation;
import com.tinqinacademy.hotel.persistance.entities.Room;
import com.tinqinacademy.hotel.persistance.more.DateUtils;
import com.tinqinacademy.hotel.persistance.repositories.ReservationsRepository;
import com.tinqinacademy.hotel.persistance.repositories.RoomsRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.validation.Validator;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@Slf4j
@Service
public class BasicInfoForRoomOperationProcessor extends BaseOperationProcessor implements BasicInfoForRoomOperation {
    private final RoomsRepository roomsRepository;
    private final ReservationsRepository reservationsRepository;

    @Autowired
    public BasicInfoForRoomOperationProcessor(RoomsRepository roomsRepository, ReservationsRepository reservationsRepository,
                                              ConversionService conversionService, ErrorMapper errorMapper, Validator validator) {
        super(conversionService, errorMapper, validator);
        this.roomsRepository = roomsRepository;
        this.reservationsRepository = reservationsRepository;
    }

    @Override
    public Either<Errors, BasicInfoForRoomOutput> process(BasicInfoForRoomInput input) {
        return Try.of(() -> {
                    log.info("Start basicInfoForRoom input: {}", input);

                    Optional<Room> optionalRoom = roomsRepository.findById(UUID.fromString(input.getRoomId()));
                    throwIfRoomDoesntExist(optionalRoom);

                    Room room = optionalRoom.get();

                    List<String> bedSizes = room.getBeds().stream()
                            .map(b -> b.getBedSize().toString()).collect(Collectors.toList());

                    List<Reservation> reservations = reservationsRepository.findByRoomId(UUID.fromString(input.getRoomId()));
                    List<LocalDate> datesOccupied = getDatesOccupied(reservations);

                    BasicInfoForRoomOutput output = conversionService.convert(room, BasicInfoForRoomOutput.BasicInfoForRoomOutputBuilder.class)
                            .bedSize(bedSizes)
                            .datesOccupied(datesOccupied)
                            .build();

                    log.info("End basicInfoForRoom output: {}", output);
                    return output;
        })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        Case($(instanceOf(IllegalArgumentException.class)),
                                errorMapper.handleError(throwable, HttpStatus.BAD_REQUEST))
        ));
    }
}
