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
import com.tinqinacademy.hotel.persistance.daos.BedsDao;
import com.tinqinacademy.hotel.persistance.daos.RoomsDao;
import com.tinqinacademy.hotel.persistance.entities.Beds;
import com.tinqinacademy.hotel.persistance.more.BathroomType;
import com.tinqinacademy.hotel.persistance.more.BedSize;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
@Slf4j
public class SystemServiceImpl implements SystemService {

    private final BedsDao bedsDao;
    private final RoomsDao roomsDao;

    @Autowired
    public SystemServiceImpl(BedsDao bedsDao, RoomsDao roomsDao) {
        this.bedsDao = bedsDao;
        this.roomsDao = roomsDao;
    }

    @Override
    public RegisterVisitorOutput registerVisitor(RegisterVisitorInput input) {
        log.info("Start registerVisitor input: {}", input);

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

        Beds beds = Beds.builder()
                .id(UUID.randomUUID())
                .bedCount(input.getBedCount())
                .bedSize(BedSize.getByCode(input.getBedSize()))
                .build();

        AdminCreateRoomOutput output = AdminCreateRoomOutput.builder()
                .id(String.valueOf(beds.getId()))
                .build();

        if ("".equals(BedSize.getByCode(input.getBedSize()).toString()) ||
                "".equals(BathroomType.getByCode(input.getBathroomType()).toString())) {
            log.error("INVALID BED SIZE OR/AND BATHROOM TYPE");
            log.info("End adminCreateRoom output: {}", output);
            return output;
        }

        bedsDao.save(beds);

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

        try {
            UUID roomId = input.getId();

            if (roomId == null) {
                throw new IllegalArgumentException("Room not found with room number: " + input.getRoomNo());
            }

            roomsDao.updateRoom(roomId, input.getFloor(), input.getBathroomType(), input.getPrice());

            AdminUpdateInfoForRoomOutput output = AdminUpdateInfoForRoomOutput.builder()
                    .id(roomId)
                    .build();

            log.info("End adminUpdateInfoForRoom output: {}", output);
            return output;
        } catch (Exception e) {
            log.error("Error updating room: ", e);
            throw new RuntimeException("Failed to update room", e);
        }
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

        roomsDao.delete(input.getId());

        DeleteRoomOutput output = DeleteRoomOutput.builder()
                .build();

        log.info("End deleteRoom output: {}", output);
        return output;
    }
}
