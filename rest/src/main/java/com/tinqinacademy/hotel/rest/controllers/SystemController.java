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
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/system")
public class SystemController {

    private SystemService systemService;
    private ObjectMapper objectMapper;

    @Autowired
    public SystemController(SystemService systemService, ObjectMapper objectMapper) {
        this.systemService = systemService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterVisitorOutput> registerVisitor(
            @Valid @RequestBody RegisterVisitorInput registerVisitorInput) {

        return new ResponseEntity<>(systemService.registerVisitor(registerVisitorInput), HttpStatus.OK);
    }

    @GetMapping("/register")
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

    @PostMapping("/room")
    public ResponseEntity<AdminCreateRoomOutput> adminCreateRoom(
            @Valid @RequestBody AdminCreateRoomInput input) {
        return new ResponseEntity<>(systemService.adminCreateRoom(input), HttpStatus.OK);
    }

    @PutMapping("/room/{roomNo}")
    public ResponseEntity<AdminUpdateInfoForRoomOutput> adminUpdateInfoForRoom(
            @Valid @RequestBody AdminUpdateInfoForRoomInput input,
            @PathVariable("roomNo") String roomNo) {

        return new ResponseEntity<>(systemService.adminUpdateInfoForRoom(input), HttpStatus.OK);
    }

    @PatchMapping("/room/{roomNo}")
    public ResponseEntity<AdminPartialUpdateOutput> adminPartialUpdate(
            @Valid @RequestBody AdminPartialUpdateInput input) {

        return new ResponseEntity<>(systemService.adminPartialUpdate(input), HttpStatus.OK);
    }

    @DeleteMapping("/room/{id}")
    public ResponseEntity<DeleteRoomOutput> deleteRoom(
            @PathVariable("id") String id) {
        DeleteRoomInput input = DeleteRoomInput.builder()
                .id(id)
                .build();

        return new ResponseEntity<>(systemService.deleteRoom(input), HttpStatus.OK);
    }
}
