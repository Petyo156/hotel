package com.tinqinacademy.hotel.core.services;

import com.tinqinacademy.hotel.api.models.hotel.basic.info.BasicInfoForRoomInput;
import com.tinqinacademy.hotel.api.models.hotel.basic.info.BasicInfoForRoomOutput;
import com.tinqinacademy.hotel.api.models.hotel.book.room.BookSpecifiedRoomInput;
import com.tinqinacademy.hotel.api.models.hotel.book.room.BookSpecifiedRoomOutput;
import com.tinqinacademy.hotel.api.models.hotel.check.room.CheckRoomAvailabilityInput;

import com.tinqinacademy.hotel.api.models.hotel.check.room.CheckRoomAvailabilityOutput;
import com.tinqinacademy.hotel.api.models.hotel.unbook.booked.room.UnbookBookedRoomInput;
import com.tinqinacademy.hotel.api.models.hotel.unbook.booked.room.UnbookBookedRoomOutput;

import com.tinqinacademy.hotel.persistance.entities.Reservation;
import com.tinqinacademy.hotel.persistance.entities.Room;
import com.tinqinacademy.hotel.persistance.more.BathroomType;
import com.tinqinacademy.hotel.persistance.more.DateUtils;
import com.tinqinacademy.hotel.persistance.repositories.GuestsRepository;
import com.tinqinacademy.hotel.persistance.repositories.ReservationsRepository;
import com.tinqinacademy.hotel.persistance.repositories.RoomsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HotelServiceImpl implements HotelService {

    private final RoomsRepository roomsRepository;
    private final ReservationsRepository reservationsRepository;

    @Autowired
    public HotelServiceImpl(RoomsRepository roomsRepository, ReservationsRepository reservationsRepository) {
        this.roomsRepository = roomsRepository;
        this.reservationsRepository = reservationsRepository;
    }

    //works
    @Override
    public CheckRoomAvailabilityOutput checkRoomAvailability(CheckRoomAvailabilityInput input) {
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
    }

    //works
    @Override
    public BasicInfoForRoomOutput basicInfoForRoom(BasicInfoForRoomInput input) {
        log.info("Start basicInfoForRoom input: {}", input);

        BasicInfoForRoomOutput output = null;

        UUID uuid = UUID.fromString(input.getRoomId());
        if (roomsRepository.findById(uuid).isEmpty()) {
            throw new IllegalArgumentException("No room with uuid " + uuid + " exists");
        }

        Room room = roomsRepository.findById(uuid).get();

        List<String> bedSizes = room.getBeds().stream()
                .map(b -> b.getBedSize().toString()).collect(Collectors.toList());

        List<Reservation> reservations = reservationsRepository.findByRoomId(UUID.fromString(input.getRoomId()));
        List<LocalDate> datesOccupied = new ArrayList<>();

        for (Reservation res : reservations) {
            datesOccupied.addAll(DateUtils.getDatesBetween(res.getStartDate(), res.getEndDate()));
        }

        output = BasicInfoForRoomOutput.builder()
                .roomId(room.getId())
                .floor(room.getFloor())
                .price(room.getPrice())
                .bedSize(bedSizes)
                .bathroomType(String.valueOf(room.getBathroomType()))
                .datesOccupied(datesOccupied)
                .build();

        log.info("End basicInfoForRoom output: {}", output);
        return output;
    }

    //works
    @Override
    public BookSpecifiedRoomOutput bookSpecifiedRoom(BookSpecifiedRoomInput input) {
        log.info("Start bookSpecifiedRoom input: {}", input);

        UUID roomId = UUID.fromString(input.getRoomId());
        Room room = roomsRepository.findById(roomId).orElseThrow(() ->
                new IllegalArgumentException("No room with uuid " + roomId + " exists"));

        boolean isAvailable = reservationsRepository.findAllByRoomIdAndStartDateBeforeAndEndDateAfter(
                roomId, input.getEndDate(), input.getStartDate()).isEmpty();

        if (!isAvailable) {
            throw new IllegalArgumentException("Room is not available for the specified dates");
        }

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
    }

    //works
    @Override
    public UnbookBookedRoomOutput unbookBookedRoom(UnbookBookedRoomInput input) {
        log.info("Start unbookBookedRoom input: {}", input);

        Optional<Reservation> reservationOptional = reservationsRepository.findById(UUID.fromString(input.getBookingId()));
        if (reservationOptional.isEmpty()) {
            throw new IllegalArgumentException("Reservation with ID " + input.getBookingId() + " doesn't exist!");
        }

        reservationsRepository.delete(reservationOptional.get());

        UnbookBookedRoomOutput output = UnbookBookedRoomOutput.builder()
                .build();

        log.info("End unbookBookedRoom output: {}", output);
        return output;
    }


}
