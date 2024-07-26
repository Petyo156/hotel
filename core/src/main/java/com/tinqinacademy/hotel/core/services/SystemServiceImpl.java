package com.tinqinacademy.hotel.core.services;


import com.tinqinacademy.hotel.api.models.system.admin.create.room.AdminCreateRoomInput;
import com.tinqinacademy.hotel.api.models.system.admin.create.room.AdminCreateRoomOutput;
import com.tinqinacademy.hotel.api.models.system.admin.partial.update.AdminPartialUpdateInput;
import com.tinqinacademy.hotel.api.models.system.admin.partial.update.AdminPartialUpdateOutput;
import com.tinqinacademy.hotel.api.models.system.admin.report.visitor.AdminReportVisitorInput;
import com.tinqinacademy.hotel.api.models.system.admin.report.visitor.AdminReportVisitorOutput;
import com.tinqinacademy.hotel.api.models.system.admin.update.info.forroom.AdminUpdateInfoForRoomInput;
import com.tinqinacademy.hotel.api.models.system.admin.update.info.forroom.AdminUpdateInfoForRoomOutput;
import com.tinqinacademy.hotel.api.models.system.delete.room.DeleteRoomInput;
import com.tinqinacademy.hotel.api.models.system.delete.room.DeleteRoomOutput;
import com.tinqinacademy.hotel.api.models.system.register.visitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.api.models.system.register.visitor.RegisterVisitorOutput;
import com.tinqinacademy.hotel.persistance.entities.*;
import com.tinqinacademy.hotel.persistance.more.BathroomType;
import com.tinqinacademy.hotel.persistance.more.BedSize;
import com.tinqinacademy.hotel.persistance.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class SystemServiceImpl implements SystemService {

    private final RoomsRepository roomsRepository;
    private final GuestsRepository guestsRepository;
    private final ReservationsRepository reservationsRepository;
    private final UsersRepository usersRepository;
    private final BedsRepository bedsRepository;

    @Autowired
    public SystemServiceImpl(RoomsRepository roomsRepository, GuestsRepository guestsRepository,
                             ReservationsRepository reservationsRepository, UsersRepository usersRepository, BedsRepository bedsRepository) {
        this.roomsRepository = roomsRepository;
        this.guestsRepository = guestsRepository;
        this.reservationsRepository = reservationsRepository;
        this.usersRepository = usersRepository;
        this.bedsRepository = bedsRepository;
    }

    @Override
    public RegisterVisitorOutput registerVisitor(RegisterVisitorInput input) {
        log.info("Start registerVisitor input: {}", input);

        Room room = roomsRepository.getRoomIdByRoomNumber(input.getRoomNumber());
        if (room == null) {
            throw new IllegalArgumentException("Room not found");
        }

        List<Reservation> existingReservations = reservationsRepository.findByRoomId(room.getId());

        for (Reservation reservation : existingReservations) {
            if (input.getStartDate().isBefore(reservation.getEndDate()) && input.getEndDate().isAfter(reservation.getStartDate())) {
                throw new IllegalArgumentException("The room is already reserved for the given period.");
            }
        }

        Guest guest = Guest.builder()
                .IdCardNumber(input.getCardNoID())
                .IdCardIssueDate(input.getCardIssueDateID())
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .phoneNo(input.getPhoneNo())
                .IdCardValidity(input.getCardValidityID())
                .IdCardAuthority(input.getCardIssueAuthorityID())
                .reservations(new ArrayList<>())
                .build();

        guestsRepository.save(guest);

        User user = usersRepository.getByEmail("lol1@abv.bg");
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        Reservation reservation = Reservation.builder()
                .roomId(room.getId())
                .userId(user.getId())
                .price(room.getPrice())
                .guests(List.of(guest))
                .startDate(input.getStartDate())
                .endDate(input.getEndDate())
                .build();

        reservationsRepository.save(reservation);

        guest.getReservations().add(reservation);
        guestsRepository.save(guest);

        RegisterVisitorOutput output = RegisterVisitorOutput.builder().build();

        log.info("End registerVisitor output: {}", output);
        return output;
    }

    @Override
    public AdminReportVisitorOutput adminReport(AdminReportVisitorInput input) {
        log.info("Start adminReport input: {}", input);

        AdminReportVisitorOutput output = AdminReportVisitorOutput.builder()
                .cardIssueAuthorityID(input.getCardNoID())
                .startDate(input.getStartDate())
                .endDate(input.getEndDate())
                .startDate(input.getStartDate())
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .phoneNo(input.getPhoneNo())
                .cardNoID(input.getCardNoID())
                .cardValidityID(input.getCardValidityID())
                .cardIssueAuthorityID(input.getCardIssueAuthorityID())
                .cardIssueDateID(input.getCardIssueDateID())
                .visitorsData(new ArrayList<>())
                .build();

        log.info("End adminReport output: {}", output);
        return output;
    }

    @Override
    public AdminCreateRoomOutput adminCreateRoom(AdminCreateRoomInput input) {
        log.info("Start adminCreateRoom input: {}", input);

        if (input.getBedSize().equals(BedSize.UNKNOWN.toString())) {
            throw new IllegalArgumentException("BedSize is UNKNOWN");
        }

        if (input.getBathroomType().equals(BathroomType.UNKNOWN.toString())) {
            throw new IllegalArgumentException("BathroomType is UNKNOWN");
        }

        Bed bed = Bed.builder()
                .bedSize(BedSize.getByCode(input.getBedSize()))
                .build();
        bedsRepository.save(bed);

        Room room = Room.builder()
                .bathroomType(BathroomType.getByCode(input.getBathroomType()))
                .floor(input.getFloor())
                .price(input.getPrice())
                .roomNumber(input.getRoomNumber())
                .beds(List.of(bed))
                .build();

        roomsRepository.save(room);

        AdminCreateRoomOutput output = AdminCreateRoomOutput.builder()
                .id(room.getId())
                .build();

        log.info("End adminCreateRoom output: {}", output);
        return output;

//exception handling
//        if ("Best id".equals(output.getId())) {
//            throw new RuntimeException("Hahahahaha");
//        }
    }

    @Override
    public AdminUpdateInfoForRoomOutput adminUpdateInfoForRoom(AdminUpdateInfoForRoomInput input) {
        log.info("Start adminUpdateInfoForRoom input: {}", input);

        UUID roomId = input.getId();
        if (!roomsRepository.existsById(roomId)) {
            throw new IllegalArgumentException("Room with id " + roomId + " doesn't exist!");
        }

        Room currentRoom = roomsRepository.getRoomById(roomId);

        Room room = Room.builder()
         //      .beds(currentRoom.getBeds())
                .roomNumber(currentRoom.getRoomNumber())
                .price(input.getPrice())
                .floor(input.getFloor())
                .bathroomType(BathroomType.getByCode(input.getBathroomType()))
                .build();

        roomsRepository.save(room);

        AdminUpdateInfoForRoomOutput output = AdminUpdateInfoForRoomOutput.builder()
                .id(roomId)
                .build();

        log.info("End adminUpdateInfoForRoom output: {}", output);
        return output;
    }

    @Override
    public AdminPartialUpdateOutput adminPartialUpdate(AdminPartialUpdateInput input) {
        log.info("Start adminPartialUpdate input: {}", input);

        AdminPartialUpdateOutput output = AdminPartialUpdateOutput.builder()
                .id("Best id")
                .build();

        log.info("End adminPartialUpdate output: {}", output);
        return output;
    }

    @Override
    public DeleteRoomOutput deleteRoom(DeleteRoomInput input) {
        log.info("Start deleteRoom input: {}", input);

        Optional<Room> roomsOptional = roomsRepository.findById(input.getId());
        if(roomsOptional.isEmpty()){
            throw new IllegalArgumentException("Room with ID " + input.getId() + " doesn't exist!");
        }

        roomsRepository.delete(roomsOptional.get());

        DeleteRoomOutput output = DeleteRoomOutput.builder()
                .build();

        log.info("End deleteRoom output: {}", output);
        return output;
    }
}
