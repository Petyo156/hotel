package com.tinqinacademy.hotel.rest.controllers.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.hotel.api.models.operations.system.admincreateroom.AdminCreateRoomInput;
import com.tinqinacademy.hotel.api.models.operations.system.adminpartialupdate.AdminPartialUpdateInput;
import com.tinqinacademy.hotel.api.models.operations.system.adminreportvisitor.AdminReportVisitorInput;
import com.tinqinacademy.hotel.api.models.operations.system.adminupdateinfoforroom.AdminUpdateInfoForRoomInput;
import com.tinqinacademy.hotel.api.models.operations.system.deleteroom.DeleteRoomInput;
import com.tinqinacademy.hotel.api.models.operations.system.registervisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.core.processors.system.*;
import com.tinqinacademy.hotel.core.services.SystemService;
import com.tinqinacademy.hotel.rest.controllers.config.RestApiMapping;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
public class SystemController extends BaseController {

    private SystemService systemService;
    private ObjectMapper objectMapper;

    private final RegisterVisitorOperationProcessor registerVisitorOperationProcessor;
    private final AdminReportVisitorOperationProcessor adminReportVisitorOperationProcessor;
    private final AdminCreateRoomOperationProcessor adminCreateRoomOperationProcessor;
    private final AdminUpdateInfoForRoomOperationProcessor adminUpdateInfoForRoomOperationProcessor;
    private final AdminPartialUpdateOperationProcessor adminPartialUpdateOperationProcessor;
    private final DeleteRoomOperationProcessor deleteRoomOperationProcessor;

    @Autowired
    public SystemController(SystemService systemService, ObjectMapper objectMapper, RegisterVisitorOperationProcessor registerVisitorOperationProcessor,
                            AdminReportVisitorOperationProcessor adminReportVisitorOperationProcessor, AdminCreateRoomOperationProcessor adminCreateRoomOperationProcessor,
                            AdminUpdateInfoForRoomOperationProcessor adminUpdateInfoForRoomOperationProcessor, AdminPartialUpdateOperationProcessor adminPartialUpdateOperationProcessor, DeleteRoomOperationProcessor deleteRoomOperationProcessor) {
        this.systemService = systemService;
        this.objectMapper = objectMapper;
        this.registerVisitorOperationProcessor = registerVisitorOperationProcessor;
        this.adminReportVisitorOperationProcessor = adminReportVisitorOperationProcessor;
        this.adminCreateRoomOperationProcessor = adminCreateRoomOperationProcessor;
        this.adminUpdateInfoForRoomOperationProcessor = adminUpdateInfoForRoomOperationProcessor;
        this.adminPartialUpdateOperationProcessor = adminPartialUpdateOperationProcessor;
        this.deleteRoomOperationProcessor = deleteRoomOperationProcessor;
    }

    @PostMapping(RestApiMapping.POST_registerVisitor_PATH)
    public ResponseEntity<?> registerVisitor(
            @Valid @RequestBody RegisterVisitorInput input) {

        return handleOperation(registerVisitorOperationProcessor.process(input), HttpStatus.BAD_REQUEST);
        //return new ResponseEntity<>(systemService.registerVisitor(registerVisitorInput), HttpStatus.OK);
    }

    @GetMapping(RestApiMapping.GET_adminReportVisitor_PATH)
    public ResponseEntity<?> adminReportVisitor(
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

        return handleOperation(adminReportVisitorOperationProcessor.process(input), HttpStatus.BAD_REQUEST);
        //return new ResponseEntity<>(systemService.adminReport(input), HttpStatus.OK);
    }

    @PostMapping(RestApiMapping.POST_adminCreateRoom_PATH)
    public ResponseEntity<?> adminCreateRoom(
            @Valid @RequestBody AdminCreateRoomInput input) {
        return handleOperation(adminCreateRoomOperationProcessor.process(input), HttpStatus.BAD_REQUEST);
        //return new ResponseEntity<>(systemService.adminCreateRoom(input), HttpStatus.OK);
    }

    @PutMapping(RestApiMapping.PUT_adminUpdateInfoForRoom_PATH)
    public ResponseEntity<?> adminUpdateInfoForRoom(
            @Valid @RequestBody AdminUpdateInfoForRoomInput input,
            @PathVariable("id") String id) {

        AdminUpdateInfoForRoomInput adminUpdateInfoForRoomInput = input.toBuilder()
                .id(id)
                .build();

        return handleOperation(adminUpdateInfoForRoomOperationProcessor.process(input), HttpStatus.BAD_REQUEST);
//        try {
//            AdminUpdateInfoForRoomOutput output = systemService.adminUpdateInfoForRoom(adminUpdateInfoForRoomInput);
//            return new ResponseEntity<>(output, HttpStatus.OK);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
    }

    @PatchMapping(value = RestApiMapping.PATCH_adminPartialUpdate_PATH, consumes = "application/json-patch+json")
    public ResponseEntity<?> adminPartialUpdate(
            @Valid @RequestBody AdminPartialUpdateInput adminPartialUpdateInput,
            @PathVariable("roomId") String id) {

        AdminPartialUpdateInput input = adminPartialUpdateInput.toBuilder()
                .roomId(UUID.fromString(id))
                .build();

        return handleOperation(adminPartialUpdateOperationProcessor.process(input), HttpStatus.BAD_REQUEST);
        //return new ResponseEntity<>(systemService.adminPartialUpdate(input), HttpStatus.OK);
    }

    @DeleteMapping(RestApiMapping.DELETE_deleteRoom_PATH)
    public ResponseEntity<?> deleteRoom(
            @PathVariable("id") String id) {
        DeleteRoomInput input = DeleteRoomInput.builder()
                .id(id)
                .build();

        return handleOperation(deleteRoomOperationProcessor.process(input), HttpStatus.BAD_REQUEST);
        //return new ResponseEntity<>(systemService.deleteRoom(input), HttpStatus.OK);
    }
}
