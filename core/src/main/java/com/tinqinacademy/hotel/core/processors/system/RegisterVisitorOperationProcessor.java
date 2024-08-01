package com.tinqinacademy.hotel.core.processors.system;

import com.tinqinacademy.hotel.api.models.exceptions.Errors;
import com.tinqinacademy.hotel.api.models.operations.system.admincreateroom.AdminCreateRoomInput;
import com.tinqinacademy.hotel.api.models.operations.system.registervisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.api.models.operations.system.registervisitor.RegisterVisitorOperation;
import com.tinqinacademy.hotel.api.models.operations.system.registervisitor.RegisterVisitorOutput;
import com.tinqinacademy.hotel.api.models.operations.system.registervisitor.RegisterVisitorsDataInput;
import com.tinqinacademy.hotel.core.errorhandling.ErrorMapper;
import com.tinqinacademy.hotel.core.processors.BaseOperationProcessor;
import com.tinqinacademy.hotel.persistance.entities.Guest;
import com.tinqinacademy.hotel.persistance.entities.Reservation;
import com.tinqinacademy.hotel.persistance.entities.Room;
import com.tinqinacademy.hotel.persistance.entities.User;
import com.tinqinacademy.hotel.persistance.more.BathroomType;
import com.tinqinacademy.hotel.persistance.repositories.GuestsRepository;
import com.tinqinacademy.hotel.persistance.repositories.ReservationsRepository;
import com.tinqinacademy.hotel.persistance.repositories.RoomsRepository;
import com.tinqinacademy.hotel.persistance.repositories.UsersRepository;
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

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@Slf4j
@Service
public class RegisterVisitorOperationProcessor extends BaseOperationProcessor implements RegisterVisitorOperation {

    private final RoomsRepository roomsRepository;
    private final ReservationsRepository reservationsRepository;
    private final UsersRepository usersRepository;
    private final GuestsRepository guestsRepository;

    public RegisterVisitorOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper, Validator validator,
                                             RoomsRepository roomsRepository, ReservationsRepository reservationsRepository, UsersRepository usersRepository, GuestsRepository guestsRepository) {
        super(conversionService, errorMapper, validator);
        this.roomsRepository = roomsRepository;
        this.reservationsRepository = reservationsRepository;
        this.usersRepository = usersRepository;
        this.guestsRepository = guestsRepository;
    }

    @Override
    public Either<Errors, RegisterVisitorOutput> process(RegisterVisitorInput input) {
        return Try.of(() -> {
                    log.info("Start registerVisitor input: {}", input);

                    Optional<Room> room = roomsRepository.findByRoomNumber(input.getRoomNumber());
                    throwIfRoomDoesntExist(room);

                    Optional<Reservation> reservation = reservationsRepository.findByStartDateAndEndDate
                            (input.getStartDate(), input.getEndDate());
                    throwIfReservationDoesntExist(reservation);

                    List<Guest> guestList = getAllGuestsForReservation(input);

                    guestsRepository.saveAll(guestList);

                    reservation.get().setGuests(guestList);
                    reservationsRepository.save(reservation.get());

                    Optional<User> user = usersRepository.findByEmail("lol1@abv.bg");
                    throwIfUserDoesntExist(user);

                    RegisterVisitorOutput output = RegisterVisitorOutput.builder().build();

                    log.info("End registerVisitor output: {}", output);
                    return output;
                })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        Case($(instanceOf(IllegalArgumentException.class)),
                                errorMapper.handleError(throwable, HttpStatus.BAD_REQUEST))
                ));
    }
}
