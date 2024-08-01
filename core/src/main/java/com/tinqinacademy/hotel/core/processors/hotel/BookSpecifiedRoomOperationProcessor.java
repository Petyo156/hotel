package com.tinqinacademy.hotel.core.processors.hotel;

import com.tinqinacademy.hotel.api.models.exceptions.Errors;
import com.tinqinacademy.hotel.api.models.operations.hotel.bookroom.BookSpecifiedRoomInput;
import com.tinqinacademy.hotel.api.models.operations.hotel.bookroom.BookSpecifiedRoomOperation;
import com.tinqinacademy.hotel.api.models.operations.hotel.bookroom.BookSpecifiedRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.hotel.checkroom.CheckRoomAvailabilityOutput;
import com.tinqinacademy.hotel.core.errorhandling.ErrorMapper;
import com.tinqinacademy.hotel.core.processors.BaseOperationProcessor;
import com.tinqinacademy.hotel.persistance.entities.Reservation;
import com.tinqinacademy.hotel.persistance.entities.Room;
import com.tinqinacademy.hotel.persistance.repositories.ReservationsRepository;
import com.tinqinacademy.hotel.persistance.repositories.RoomsRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@Service
@Slf4j
public class BookSpecifiedRoomOperationProcessor extends BaseOperationProcessor implements BookSpecifiedRoomOperation {
    private final RoomsRepository roomsRepository;
    private final ReservationsRepository reservationsRepository;

    @Autowired
    public BookSpecifiedRoomOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper, Validator validator,
                                               RoomsRepository roomsRepository, ReservationsRepository reservationsRepository) {
        super(conversionService, errorMapper, validator);
        this.roomsRepository = roomsRepository;
        this.reservationsRepository = reservationsRepository;
    }

    @Override
    public Either<Errors, BookSpecifiedRoomOutput> process(BookSpecifiedRoomInput input) {
        return Try.of(() -> {
                    log.info("Start bookSpecifiedRoom input: {}", input);

                    UUID roomId = UUID.fromString(input.getRoomId());
                    Optional<Room> optionalRoom = roomsRepository.findById(roomId);
                    throwIfRoomDoesntExist(optionalRoom);

                    Room room = optionalRoom.get();

                    throwIfRoomIsNotAvailableForSpecificDates(input, roomId, reservationsRepository);

                    Reservation reservation = Reservation.builder()
                            .roomId(roomId)
                            .userId(UUID.fromString(input.getUserId()))
                            .startDate(input.getStartDate())
                            .endDate(input.getEndDate())
                            .price(room.getPrice())
                            .guests(List.of())
                            .build();

                    reservationsRepository.save(reservation);

                    BookSpecifiedRoomOutput output = BookSpecifiedRoomOutput.builder()
                            .build();

                    log.info("End bookSpecifiedRoom output: {}", output);
                    return output;
                })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        Case($(instanceOf(IllegalArgumentException.class)),
                                errorMapper.handleError(throwable, HttpStatus.BAD_REQUEST))
                ));
    }
}
