package com.tinqinacademy.hotel.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.hotel.api.models.apimapping.RestApiMapping;
import com.tinqinacademy.hotel.api.models.operations.hotel.basicinfo.BasicInfoForRoomInput;
import com.tinqinacademy.hotel.api.models.operations.hotel.bookroom.BookSpecifiedRoomInput;
import com.tinqinacademy.hotel.api.models.operations.hotel.checkroom.CheckRoomAvailabilityInput;
import com.tinqinacademy.hotel.api.models.operations.hotel.unbookbookedroom.UnbookBookedRoomInput;
import com.tinqinacademy.hotel.core.processors.hotel.BasicInfoForRoomOperationProcessor;
import com.tinqinacademy.hotel.core.processors.hotel.BookSpecifiedRoomOperationProcessor;
import com.tinqinacademy.hotel.core.processors.hotel.CheckRoomAvailabilityOperationProcessor;
import com.tinqinacademy.hotel.core.processors.hotel.UnbookBookedRoomOperationProcessor;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class HotelController extends BaseController {
    //only in post put patch POJOS are needed validations

    private final ObjectMapper objectMapper;
    private final CheckRoomAvailabilityOperationProcessor checkRoomAvailabilityOperationProcessor;
    private final BookSpecifiedRoomOperationProcessor bookSpecifiedRoomOperationProcessor;
    private final UnbookBookedRoomOperationProcessor unbookBookedRoomOperationProcessor;

    private final BasicInfoForRoomOperationProcessor basicInfoForRoomOperationProcessor;

    @Autowired
    public HotelController(ObjectMapper objectMapper, CheckRoomAvailabilityOperationProcessor checkRoomAvailabilityOperationProcessor,
                           BookSpecifiedRoomOperationProcessor bookSpecifiedRoomOperationProcessor, UnbookBookedRoomOperationProcessor unbookBookedRoomOperationProcessor, BasicInfoForRoomOperationProcessor basicInfoForRoomOperationProcessor) {
        this.objectMapper = objectMapper;
        this.checkRoomAvailabilityOperationProcessor = checkRoomAvailabilityOperationProcessor;
        this.bookSpecifiedRoomOperationProcessor = bookSpecifiedRoomOperationProcessor;
        this.unbookBookedRoomOperationProcessor = unbookBookedRoomOperationProcessor;
        this.basicInfoForRoomOperationProcessor = basicInfoForRoomOperationProcessor;
    }

    @GetMapping(RestApiMapping.GET_checkAvailability_PATH)
    public ResponseEntity<?> checkAvailability(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate,
            @RequestParam("bedSize") String bedSize,
            @RequestParam("bathroomType") String bathroomType) {

        CheckRoomAvailabilityInput input = CheckRoomAvailabilityInput.builder()
                .endDate(endDate)
                .startDate(startDate)
                .bathroomType(bathroomType)
                .bedSize(bedSize)
                .build();

        return handleOperation(checkRoomAvailabilityOperationProcessor.process(input), HttpStatus.BAD_REQUEST);
    }

    @GetMapping(RestApiMapping.GET_basicInfoForRoom_PATH)
    public ResponseEntity<?> basicInfoForRoom(
            @PathVariable("roomId") String roomId) {

        BasicInfoForRoomInput input = BasicInfoForRoomInput.builder()
                .roomId(String.valueOf(roomId))
                .build();

        return handleOperation(basicInfoForRoomOperationProcessor.process(input), HttpStatus.BAD_REQUEST);
    }

    @PostMapping(RestApiMapping.POST_bookSpecifiedRoom_PATH)
    public ResponseEntity<?> bookSpecifiedRoom(
            @Valid @RequestBody BookSpecifiedRoomInput bookSpecifiedRoomInput,
            @PathVariable("roomId") String roomId) {

        BookSpecifiedRoomInput input = bookSpecifiedRoomInput.toBuilder()
                .roomId(roomId)
                .build();

        return handleOperation(bookSpecifiedRoomOperationProcessor.process(input), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(RestApiMapping.DELETE_unbookBookedRoom_PATH)
    public ResponseEntity<?> unbookBookedRoom(
            @PathVariable String bookingId) {

        UnbookBookedRoomInput input = UnbookBookedRoomInput.builder()
                .bookingId(bookingId)
                .build();

        return handleOperation(unbookBookedRoomOperationProcessor.process(input), HttpStatus.BAD_REQUEST);
    }

}
