package com.tinqinacademy.hotel.core.processors;

import com.tinqinacademy.hotel.api.models.base.OperationInput;
import com.tinqinacademy.hotel.api.models.base.OperationOutput;
import com.tinqinacademy.hotel.api.models.exceptions.ErrorResponse;
import com.tinqinacademy.hotel.api.models.exceptions.Errors;
import com.tinqinacademy.hotel.api.models.operations.hotel.basicinfo.BasicInfoForRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.hotel.bookroom.BookSpecifiedRoomInput;
import com.tinqinacademy.hotel.api.models.operations.system.admincreateroom.AdminCreateRoomInput;
import com.tinqinacademy.hotel.api.models.operations.system.registervisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.api.models.operations.system.registervisitor.RegisterVisitorsDataInput;
import com.tinqinacademy.hotel.core.errorhandling.ErrorMapper;
import com.tinqinacademy.hotel.persistance.entities.Guest;
import com.tinqinacademy.hotel.persistance.entities.Reservation;
import com.tinqinacademy.hotel.persistance.entities.Room;
import com.tinqinacademy.hotel.persistance.entities.User;
import com.tinqinacademy.hotel.persistance.more.BathroomType;
import com.tinqinacademy.hotel.persistance.more.BedSize;
import com.tinqinacademy.hotel.persistance.more.DateUtils;
import com.tinqinacademy.hotel.persistance.repositories.ReservationsRepository;
import io.vavr.control.Either;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


public class BaseOperationProcessor {
    protected final ConversionService conversionService;
    protected final ErrorMapper errorMapper;
    protected final Validator validator;

    @Autowired
    public BaseOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper, Validator validator) {
        this.conversionService = conversionService;
        this.errorMapper = errorMapper;
        this.validator = validator;
    }

    protected Either<Errors, ? extends OperationInput> validateInput(OperationInput input) {
        Set<ConstraintViolation<OperationInput>> violations = validator.validate(input);

        if (!violations.isEmpty()) {
            List<String> errorMessages = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());

            ErrorResponse errorResponse = ErrorResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message(String.join(", ", errorMessages))
                    .build();

            return Either.left(errorResponse);
        }

        return Either.right(input);
    }


    protected List<Guest> getAllGuestsForReservation(RegisterVisitorInput input) {
        List<Guest> guestList = new ArrayList<>();
        for (RegisterVisitorsDataInput dataInput : input.getRegisterVisitorsDataInputList()) {
            Guest guest = conversionService.convert(dataInput, Guest.GuestBuilder.class)
                    .build();
            guestList.add(guest);
        }
        return guestList;
    }

    protected void throwIfReservationDoesntExist(Optional<Reservation> reservation) {
        if (reservation.isEmpty()) {
            throw new IllegalArgumentException("Reservation not found");
        }
    }

    protected void throwIfRoomDoesntExist(Optional<Room> room) {
        if (room.isEmpty()) {
            throw new IllegalArgumentException("Room not found");
        }
    }

    protected void throwIfUserDoesntExist(Optional<User> user) {
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
    }

    protected void throwIfBathroomTypeDoesntExist(AdminCreateRoomInput input) {
        if (BathroomType.UNKNOWN.equals(BathroomType.getByCode(input.getBathroomType()))) {
            throw new IllegalArgumentException("Invalid bathroomtype - " + input.getBathroomType());
        }
    }

    protected List<BedSize> convertBedsStringsFromInputToBedSize(List<String> input) {
        List<BedSize> beds = new ArrayList<>();
        for (String bedString : input) {
            if (BedSize.getByCode(bedString).equals(BedSize.UNKNOWN)) {
                throw new IllegalArgumentException("Invalid bedsize - " + bedString);
            }
            beds.add(BedSize.getByCode(bedString));
        }
        return beds;
    }

    protected void throwIfRoomIsNotAvailableForSpecificDates(BookSpecifiedRoomInput input, UUID roomId,
                                                             ReservationsRepository reservationsRepository) {
        boolean isAvailable = reservationsRepository.findAllByRoomIdAndStartDateBeforeAndEndDateAfter(
                roomId, input.getEndDate(), input.getStartDate()).isEmpty();

        if (!isAvailable) {
            throw new IllegalArgumentException("Room is not available for the specified dates");
        }
    }

    protected List<LocalDate> getDatesOccupied(List<Reservation> reservations) {
        List<LocalDate> datesOccupied = new ArrayList<>();

        for (Reservation res : reservations) {
            datesOccupied.addAll(DateUtils.getDatesBetween(res.getStartDate(), res.getEndDate()));
        }
        return datesOccupied;
    }
}
