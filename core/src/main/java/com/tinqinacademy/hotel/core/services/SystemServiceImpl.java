package com.tinqinacademy.hotel.core.services;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
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
import com.tinqinacademy.hotel.api.models.system.register.visitor.RegisterVisitorsDataInput;
import com.tinqinacademy.hotel.persistance.entities.*;
import com.tinqinacademy.hotel.persistance.more.BathroomType;
import com.tinqinacademy.hotel.persistance.more.BedSize;
import com.tinqinacademy.hotel.persistance.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    private final ObjectMapper objectMapper;

    @Autowired
    public SystemServiceImpl(RoomsRepository roomsRepository, GuestsRepository guestsRepository,
                             ReservationsRepository reservationsRepository, UsersRepository usersRepository,
                             BedsRepository bedsRepository, ObjectMapper objectMapper) {
        this.roomsRepository = roomsRepository;
        this.guestsRepository = guestsRepository;
        this.reservationsRepository = reservationsRepository;
        this.usersRepository = usersRepository;
        this.bedsRepository = bedsRepository;
        this.objectMapper = objectMapper;
    }

    //works
    @Override
    public RegisterVisitorOutput registerVisitor(RegisterVisitorInput input) {
        log.info("Start registerVisitor input: {}", input);

        Optional<Room> room = roomsRepository.findByRoomNumber(input.getRoomNumber());
        if (room.isEmpty()) {
            throw new IllegalArgumentException("Room not found");
        }

        Optional<Reservation> reservation = reservationsRepository.findByStartDateAndEndDate
                (input.getStartDate(), input.getEndDate());
        if (reservation.isEmpty()) {
            throw new IllegalArgumentException("Reservation not found");
        }

        List<Guest> guestList = new ArrayList<>();
        for (RegisterVisitorsDataInput dataInput : input.getRegisterVisitorsDataInputList()) {
            Guest guest = Guest.builder()
                    .IdCardNumber(dataInput.getCardNoID())
                    .IdCardIssueDate(dataInput.getCardIssueDateID())
                    .firstName(dataInput.getFirstName())
                    .lastName(dataInput.getLastName())
                    .phoneNo(dataInput.getPhoneNo())
                    .IdCardValidity(dataInput.getCardValidityID())
                    .IdCardAuthority(dataInput.getCardIssueAuthorityID())
                    .birthdate(dataInput.getBirthDate())
                    .build();
            guestList.add(guest);
        }

        guestsRepository.saveAll(guestList);

        reservation.get().setGuests(guestList);
        reservationsRepository.save(reservation.get());

        Optional<User> user = usersRepository.findByEmail("lol1@abv.bg");
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        RegisterVisitorOutput output = RegisterVisitorOutput.builder().build();

        log.info("End registerVisitor output: {}", output);
        return output;
    }

    //-not implemented
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

    //works
    @Override
    public AdminCreateRoomOutput adminCreateRoom(AdminCreateRoomInput input) {
        log.info("Start adminCreateRoom input: {}", input);

        List<BedSize> beds = new ArrayList<>();
        for (String bedString : input.getBedSizes()) {
            if (BedSize.getByCode(bedString).equals(BedSize.UNKNOWN)) {
                throw new IllegalArgumentException("Invalid bedsize - " + bedString);
            }
            beds.add(BedSize.getByCode(bedString));
        }
        List<Bed> bedList  = bedsRepository.findAllByBedSizeIn(beds);

        if (BathroomType.UNKNOWN.equals(BathroomType.getByCode(input.getBathroomType()))) {
            throw new IllegalArgumentException("Invalid bathroomtype - " + input.getBathroomType());
        }

        Room room = Room.builder()
                .bathroomType(BathroomType.getByCode(input.getBathroomType()))
                .floor(input.getFloor())
                .price(input.getPrice())
                .roomNumber(input.getRoomNumber())
                .beds(bedList)
                .build();

        roomsRepository.save(room);

        AdminCreateRoomOutput output = AdminCreateRoomOutput.builder()
                .id(room.getId())
                .build();

        log.info("End adminCreateRoom output: {}", output);
        return output;
    }

    //works
    @Override
    public AdminUpdateInfoForRoomOutput adminUpdateInfoForRoom(AdminUpdateInfoForRoomInput input) {
        log.info("Start adminUpdateInfoForRoom input: {}", input);

        Optional<Room> roomsOptional = roomsRepository.findById(UUID.fromString(input.getId()));
        if (roomsOptional.isEmpty()) {
            throw new IllegalArgumentException("Room with ID " + input.getId() + " doesn't exist!");
        }

        List<BedSize> beds = new ArrayList<>();
        for (String bedString : input.getBedSizes()) {
            if (BedSize.getByCode(bedString).equals(BedSize.UNKNOWN)) {
                throw new IllegalArgumentException("Invalid bedsize - " + bedString);
            }
            beds.add(BedSize.getByCode(bedString));
        }
        List<Bed> bedList  = bedsRepository.findAllByBedSizeIn(beds);

        Room room = Room.builder()
                .id(UUID.fromString(input.getId()))
                .beds(bedList)
                .roomNumber(input.getRoomNo())
                .price(input.getPrice())
                .floor(input.getFloor())
                .bathroomType(BathroomType.getByCode(input.getBathroomType()))
                .build();

        roomsRepository.save(room);

        AdminUpdateInfoForRoomOutput output = AdminUpdateInfoForRoomOutput.builder()
                .id(room.getId())
                .build();

        log.info("End adminUpdateInfoForRoom output: {}", output);
        return output;
    }

    //works
    @Override
    public AdminPartialUpdateOutput adminPartialUpdate(AdminPartialUpdateInput input, String id) {
        log.info("Start adminPartialUpdate input: {}", input);

        Optional<Room> optionalRoom = roomsRepository.findById(UUID.fromString(id));

        if (optionalRoom.isEmpty()) {
            throw new IllegalArgumentException("Room not found with roomNumber: " + input.getRoomNumber());
        }

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
    }

    //works
    @Override
    public DeleteRoomOutput deleteRoom(DeleteRoomInput input) {
        log.info("Start deleteRoom input: {}", input);

        Optional<Room> roomsOptional = roomsRepository.findById(UUID.fromString(input.getId()));
        if (roomsOptional.isEmpty()) {
            throw new IllegalArgumentException("Room with ID " + input.getId() + " doesn't exist!");
        }

        roomsRepository.delete(roomsOptional.get());

        DeleteRoomOutput output = DeleteRoomOutput.builder()
                .build();

        log.info("End deleteRoom output: {}", output);
        return output;
    }
}
