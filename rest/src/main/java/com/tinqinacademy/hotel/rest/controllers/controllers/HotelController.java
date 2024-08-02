package com.tinqinacademy.hotel.rest.controllers.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.hotel.api.models.operations.hotel.basicinfo.BasicInfoForRoomInput;
import com.tinqinacademy.hotel.api.models.operations.hotel.bookroom.BookSpecifiedRoomInput;
import com.tinqinacademy.hotel.api.models.operations.hotel.checkroom.CheckRoomAvailabilityInput;
import com.tinqinacademy.hotel.api.models.operations.hotel.unbookbookedroom.UnbookBookedRoomInput;
import com.tinqinacademy.hotel.core.processors.hotel.BasicInfoForRoomOperationProcessor;
import com.tinqinacademy.hotel.core.processors.hotel.BookSpecifiedRoomOperationProcessor;
import com.tinqinacademy.hotel.core.processors.hotel.CheckRoomAvailabilityOperationProcessor;
import com.tinqinacademy.hotel.core.processors.hotel.UnbookBookedRoomOperationProcessor;
import com.tinqinacademy.hotel.core.services.HotelService;
import com.tinqinacademy.hotel.rest.controllers.config.RestApiMapping;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class HotelController extends BaseController {
    //only in post put patch POJOS are needed validations

    private final HotelService hotelService;
    private final ObjectMapper objectMapper;
    private final CheckRoomAvailabilityOperationProcessor checkRoomAvailabilityOperationProcessor;
    private final BookSpecifiedRoomOperationProcessor bookSpecifiedRoomOperationProcessor;
    private final UnbookBookedRoomOperationProcessor unbookBookedRoomOperationProcessor;

    private final BasicInfoForRoomOperationProcessor basicInfoForRoomOperationProcessor;

    @Autowired
    public HotelController(HotelService hotelService, ObjectMapper objectMapper, CheckRoomAvailabilityOperationProcessor checkRoomAvailabilityOperationProcessor,
                           BookSpecifiedRoomOperationProcessor bookSpecifiedRoomOperationProcessor, UnbookBookedRoomOperationProcessor unbookBookedRoomOperationProcessor, BasicInfoForRoomOperationProcessor basicInfoForRoomOperationProcessor) {
        this.hotelService = hotelService;
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
        //return new ResponseEntity<>(hotelService.checkRoomAvailability(input), HttpStatus.OK);
    }

    @GetMapping(RestApiMapping.GET_basicInfoForRoom_PATH)
    public ResponseEntity<?> basicInfoForRoom(
            @PathVariable("roomId") String roomId) {

        BasicInfoForRoomInput input = BasicInfoForRoomInput.builder()
                .roomId(String.valueOf(roomId))
                .build();

        return handleOperation(basicInfoForRoomOperationProcessor.process(input), HttpStatus.BAD_REQUEST);
        //return new ResponseEntity<>(hotelService.basicInfoForRoom(input), HttpStatus.OK);
    }

    @PostMapping(RestApiMapping.POST_bookSpecifiedRoom_PATH)
    public ResponseEntity<?> bookSpecifiedRoom(
            @Valid @RequestBody BookSpecifiedRoomInput input,
            @PathVariable("roomId") String roomId) {

        BookSpecifiedRoomInput bookSpecifiedRoomInput = input.toBuilder()
                .roomId(String.valueOf(roomId))
                .build();

        return handleOperation(bookSpecifiedRoomOperationProcessor.process(input), HttpStatus.BAD_REQUEST);
        //return new ResponseEntity<>(hotelService.bookSpecifiedRoom(bookSpecifiedRoomInput), HttpStatus.OK);
    }

    @DeleteMapping(RestApiMapping.DELETE_unbookBookedRoom_PATH)
    public ResponseEntity<?> unbookBookedRoom(
            @PathVariable String bookingId) {

        UnbookBookedRoomInput input = UnbookBookedRoomInput.builder()
                .bookingId(bookingId)
                .build();

        return handleOperation(unbookBookedRoomOperationProcessor.process(input), HttpStatus.BAD_REQUEST);
        //return new ResponseEntity<>(hotelService.unbookBookedRoom(input), HttpStatus.OK);
    }

}
