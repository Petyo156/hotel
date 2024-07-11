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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class SystemServiceImpl implements SystemService {

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

        AdminCreateRoomOutput output = AdminCreateRoomOutput.builder()
                .id("Best id")
                .build();

//        if ("Best id".equals(output.getId())) {
//            throw new RuntimeException("Hahahahaha");
//        }

        log.info("End adminCreateRoom output: {}", output);
        return output;
    }

    @Override
    public AdminUpdateInfoForRoomOutput adminUpdateInfoForRoom(AdminUpdateInfoForRoomInput input) {
        log.info("Start adminUpdateInfoForRoom input: {}", input);

        AdminUpdateInfoForRoomOutput output = AdminUpdateInfoForRoomOutput.builder()
                .id("Best id")
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
