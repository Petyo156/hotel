package com.tinqinacademy.hotel.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.tinqinacademy.hotel.core.services.SystemService;
import com.tinqinacademy.hotel.rest.controllers.config.RestApiMapping;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
public class SystemController {

    private SystemService systemService;
    private ObjectMapper objectMapper;

    @Autowired
    public SystemController(SystemService systemService, ObjectMapper objectMapper) {
        this.systemService = systemService;
        this.objectMapper = objectMapper;
    }

    @PostMapping(RestApiMapping.POST_registerVisitor_PATH)
    public ResponseEntity<RegisterVisitorOutput> registerVisitor(
            @Valid @RequestBody RegisterVisitorInput registerVisitorInput) {

        return new ResponseEntity<>(systemService.registerVisitor(registerVisitorInput), HttpStatus.OK);
    }

    @GetMapping(RestApiMapping.GET_adminReportVisitor_PATH)
    public ResponseEntity<AdminReportVisitorOutput> adminReportVisitor(
            @RequestParam("visitorsData") List<String> visitorsData,
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("phoneNo") String phoneNo,
            @RequestParam("cardNoID") String cardNoID,
            @RequestParam("cardValidityID") String cardValidityID,
            @RequestParam("cardIssueAuthorityID") String cardIssueAuthorityID,
            @RequestParam("cardIssueDateID") LocalDate cardIssueDateID) {

        AdminReportVisitorInput input = AdminReportVisitorInput.builder()
                .cardIssueAuthorityID(cardIssueAuthorityID)
                .endDate(endDate)
                .cardNoID(cardNoID)
                .cardIssueDateID(cardIssueDateID)
                .startDate(startDate)
                .firstName(firstName)
                .lastName(lastName)
                .phoneNo(phoneNo)
                .cardValidityID(cardValidityID)
                .build();

        return new ResponseEntity<>(systemService.adminReport(input), HttpStatus.OK);
    }

    @PostMapping(RestApiMapping.POST_adminCreateRoom_PATH)
    public ResponseEntity<AdminCreateRoomOutput> adminCreateRoom(
            @Valid @RequestBody AdminCreateRoomInput input) {
        return new ResponseEntity<>(systemService.adminCreateRoom(input), HttpStatus.OK);
    }

    @PutMapping(RestApiMapping.PUT_adminUpdateInfoForRoom_PATH)
    public ResponseEntity<AdminUpdateInfoForRoomOutput> adminUpdateInfoForRoom(
            @Valid @RequestBody AdminUpdateInfoForRoomInput input,
            @PathVariable("id") String id) {

        AdminUpdateInfoForRoomInput adminUpdateInfoForRoomInput = input.toBuilder()
                .id(id)
                .build();

        try {
            AdminUpdateInfoForRoomOutput output = systemService.adminUpdateInfoForRoom(adminUpdateInfoForRoomInput);
            return new ResponseEntity<>(output, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(value = RestApiMapping.PATCH_adminPartialUpdate_PATH, consumes = "application/json-patch+json")
    public ResponseEntity<AdminPartialUpdateOutput> adminPartialUpdate(
            @Valid @RequestBody AdminPartialUpdateInput input,
            @PathVariable("id") String id) {

        return new ResponseEntity<>(systemService.adminPartialUpdate(input, id), HttpStatus.OK);
    }

    @DeleteMapping(RestApiMapping.DELETE_deleteRoom_PATH)
    public ResponseEntity<DeleteRoomOutput> deleteRoom(
            @PathVariable("id") String id) {
        DeleteRoomInput input = DeleteRoomInput.builder()
                .id(id)
                .build();

        return new ResponseEntity<>(systemService.deleteRoom(input), HttpStatus.OK);
    }
}
