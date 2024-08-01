package com.tinqinacademy.hotel.core.services;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import com.tinqinacademy.hotel.api.models.operations.system.admincreateroom.AdminCreateRoomInput;
import com.tinqinacademy.hotel.api.models.operations.system.admincreateroom.AdminCreateRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.system.adminpartialupdate.AdminPartialUpdateInput;
import com.tinqinacademy.hotel.api.models.operations.system.adminpartialupdate.AdminPartialUpdateOutput;
import com.tinqinacademy.hotel.api.models.operations.system.adminreportvisitor.AdminReportVisitorInput;
import com.tinqinacademy.hotel.api.models.operations.system.adminreportvisitor.AdminReportVisitorOutput;
import com.tinqinacademy.hotel.api.models.operations.system.adminupdateinfoforroom.AdminUpdateInfoForRoomInput;
import com.tinqinacademy.hotel.api.models.operations.system.adminupdateinfoforroom.AdminUpdateInfoForRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.system.deleteroom.DeleteRoomInput;
import com.tinqinacademy.hotel.api.models.operations.system.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.system.registervisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.api.models.operations.system.registervisitor.RegisterVisitorOutput;
import com.tinqinacademy.hotel.api.models.operations.system.registervisitor.RegisterVisitorsDataInput;
import com.tinqinacademy.hotel.persistance.entities.*;
import com.tinqinacademy.hotel.persistance.more.BathroomType;
import com.tinqinacademy.hotel.persistance.more.BedSize;
import com.tinqinacademy.hotel.persistance.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
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

    private final ConversionService conversionService;

    @Autowired
    public SystemServiceImpl(RoomsRepository roomsRepository, GuestsRepository guestsRepository,
                             ReservationsRepository reservationsRepository, UsersRepository usersRepository,
                             BedsRepository bedsRepository, ObjectMapper objectMapper, ConversionService conversionService) {
        this.roomsRepository = roomsRepository;
        this.guestsRepository = guestsRepository;
        this.reservationsRepository = reservationsRepository;
        this.usersRepository = usersRepository;
        this.bedsRepository = bedsRepository;
        this.objectMapper = objectMapper;
        this.conversionService = conversionService;
    }

    //works   //created processor
    @Override
    public RegisterVisitorOutput registerVisitor(RegisterVisitorInput input) {
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
    }

    private List<Guest> getAllGuestsForReservation(RegisterVisitorInput input) {
        List<Guest> guestList = new ArrayList<>();
        for (RegisterVisitorsDataInput dataInput : input.getRegisterVisitorsDataInputList()) {
            Guest guest = conversionService.convert(dataInput, Guest.GuestBuilder.class)
                    .build();
            guestList.add(guest);
        }
        return guestList;
    }

    //-not implemented //created processor
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

    //works  //created processor
    @Override
    public AdminCreateRoomOutput adminCreateRoom(AdminCreateRoomInput input) {
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
    }

    //works    //created processor
    @Override
    public AdminUpdateInfoForRoomOutput adminUpdateInfoForRoom(AdminUpdateInfoForRoomInput input) {
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
    }

    private static List<BedSize> convertBedsStringsFromInputToBedSize(List<String> input) {
        List<BedSize> beds = new ArrayList<>();
        for (String bedString : input) {
            if (BedSize.getByCode(bedString).equals(BedSize.UNKNOWN)) {
                throw new IllegalArgumentException("Invalid bedsize - " + bedString);
            }
            beds.add(BedSize.getByCode(bedString));
        }
        return beds;
    }

    //works   //created processor
    @Override
    public AdminPartialUpdateOutput adminPartialUpdate(AdminPartialUpdateInput input) {
        log.info("Start adminPartialUpdate input: {}", input);

        Optional<Room> optionalRoom = roomsRepository.findById(input.getRoomId());
        throwIfRoomDoesntExist(optionalRoom);

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

    //works  //created processor
    @Override
    public DeleteRoomOutput deleteRoom(DeleteRoomInput input) {
        log.info("Start deleteRoom input: {}", input);

        Optional<Room> roomsOptional = roomsRepository.findById(UUID.fromString(input.getId()));
        throwIfRoomDoesntExist(roomsOptional);

        roomsRepository.delete(roomsOptional.get());

        DeleteRoomOutput output = DeleteRoomOutput.builder()
                .build();

        log.info("End deleteRoom output: {}", output);
        return output;
    }

    private static void throwIfReservationDoesntExist(Optional<Reservation> reservation) {
        if (reservation.isEmpty()) {
            throw new IllegalArgumentException("Reservation not found");
        }
    }

    private static void throwIfRoomDoesntExist(Optional<Room> room) {
        if (room.isEmpty()) {
            throw new IllegalArgumentException("Room not found");
        }
    }

    private static void throwIfBathroomTypeDoesntExist(AdminCreateRoomInput input) {
        if (BathroomType.UNKNOWN.equals(BathroomType.getByCode(input.getBathroomType()))) {
            throw new IllegalArgumentException("Invalid bathroomtype - " + input.getBathroomType());
        }
    }

    private static void throwIfUserDoesntExist(Optional<User> user) {
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
    }
}
