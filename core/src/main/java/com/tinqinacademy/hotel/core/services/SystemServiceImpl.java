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
import com.tinqinacademy.hotel.persistance.entities.Beds;
import com.tinqinacademy.hotel.persistance.entities.Guests;
import com.tinqinacademy.hotel.persistance.entities.Reservations;
import com.tinqinacademy.hotel.persistance.entities.Rooms;
import com.tinqinacademy.hotel.persistance.more.BathroomType;
import com.tinqinacademy.hotel.persistance.more.BedSize;
import com.tinqinacademy.hotel.persistance.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

//        List<Reservations> existingReservations = reservationsRepository.findByCardNoId(input.getCardNoID());
//
//        for (Reservations reservation : existingReservations) {
//            if (input.getStartDate().isBefore(reservation.getEndDate()) && input.getEndDate().isAfter(reservation.getStartDate())) {
//                throw new IllegalArgumentException("The room is already reserved for the given period.");
//            }
//        }
//
//        Guests guests = Guests.builder()
//                .IdCardNumber(input.getCardNoID())
//                .IdCardIssueDate(input.getCardIssueDateID())
//                .firstName(input.getFirstName())
//                .lastName(input.getLastName())
//                .phoneNo(input.getPhoneNo())
//                .reservations(new ArrayList<>())
//                .build();
//
//        guestsRepository.save(guests);
//
//        Reservations reservations = Reservations.builder()
//                .roomId(roomsRepository.getRoomsIdByRoomNumber(input.getRoomNumber()).getId())
//                //hardcoded
//                .userId(usersRepository.getByEmail("lol1@abv.bg").getId())
//                .price(roomsRepository.getRoomsIdByRoomNumber(input.getRoomNumber()).getPrice())
//                .guests(List.of(guests))
//                .startDate(input.getStartDate())
//                .endDate(input.getEndDate())
//                .build();
//
//        reservationsRepository.save(reservations);
//
//        guests.getReservations().add(reservations);
//        guestsRepository.save(guests);

        RegisterVisitorOutput output = RegisterVisitorOutput.builder()
                .build();

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

        Beds beds = Beds.builder()
                .bedSize(BedSize.getByCode(input.getBedSize()))
                .build();
        bedsRepository.save(beds);

        Rooms rooms = Rooms.builder()
                .bathroomType(BathroomType.getByCode(input.getBathroomType()))
                .floor(input.getFloor())
                .price(input.getPrice())
                .roomNumber(input.getRoomNumber())
                .beds(List.of(beds))
                .build();

        roomsRepository.save(rooms);

        AdminCreateRoomOutput output = AdminCreateRoomOutput.builder()
                .id(rooms.getId())
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

        Rooms currentRoom = roomsRepository.getRoomsById(roomId);



        Rooms rooms = Rooms.builder()
//                .beds(currentRoom.getBeds().add())
                .roomNumber(currentRoom.getRoomNumber())
                .price(input.getPrice())
                .floor(input.getFloor())
                .bathroomType(BathroomType.getByCode(input.getBathroomType()))
                .build();

        roomsRepository.save(rooms);

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

        DeleteRoomOutput output = DeleteRoomOutput.builder()
                .build();

        log.info("End deleteRoom output: {}", output);
        return output;
    }
}
