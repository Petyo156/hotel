package com.tinqinacademy.hotel.rest.controllers.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.hotel.api.models.operations.hotel.basicinfo.BasicInfoForRoomInput;
import com.tinqinacademy.hotel.api.models.operations.hotel.basicinfo.BasicInfoForRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.hotel.bookroom.BookSpecifiedRoomInput;
import com.tinqinacademy.hotel.api.models.operations.hotel.bookroom.BookSpecifiedRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.hotel.checkroom.CheckRoomAvailabilityInput;
import com.tinqinacademy.hotel.api.models.operations.hotel.checkroom.CheckRoomAvailabilityOutput;
import com.tinqinacademy.hotel.api.models.operations.hotel.unbookbookedroom.UnbookBookedRoomInput;
import com.tinqinacademy.hotel.api.models.operations.hotel.unbookbookedroom.UnbookBookedRoomOutput;
import com.tinqinacademy.hotel.core.services.HotelService;
import com.tinqinacademy.hotel.rest.controllers.config.RestApiMapping;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class HotelController {
    //only in post put patch POJOS are needed validations

    private HotelService hotelService;
    private ObjectMapper objectMapper;

    @Autowired
    public HotelController(HotelService hotelService, ObjectMapper objectMapper) {
        this.hotelService = hotelService;
        this.objectMapper = objectMapper;
    }

    @GetMapping(RestApiMapping.GET_checkAvailability_PATH)
    public ResponseEntity<CheckRoomAvailabilityOutput> checkAvailability(
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

        return new ResponseEntity<>(hotelService.checkRoomAvailability(input), HttpStatus.OK);
    }

    @GetMapping(RestApiMapping.GET_basicInfoForRoom_PATH)
    public ResponseEntity<BasicInfoForRoomOutput> basicInfoForRoom(
            @PathVariable("roomId")
            String roomId) {

        BasicInfoForRoomInput input = BasicInfoForRoomInput.builder()
                .roomId(String.valueOf(roomId))
                .build();

        return new ResponseEntity<>(hotelService.basicInfoForRoom(input), HttpStatus.OK);
    }

    @PostMapping(RestApiMapping.POST_bookSpecifiedRoom_PATH)
    public ResponseEntity<BookSpecifiedRoomOutput> bookSpecifiedRoom(
            @Valid @RequestBody BookSpecifiedRoomInput input,
            @PathVariable("roomId") String roomId) {

        BookSpecifiedRoomInput bookSpecifiedRoomInput = input.toBuilder()
                .roomId(String.valueOf(roomId))
                .build();

        return new ResponseEntity<>(hotelService.bookSpecifiedRoom(bookSpecifiedRoomInput), HttpStatus.OK);
    }

    @DeleteMapping(RestApiMapping.DELETE_unbookBookedRoom_PATH)
    public ResponseEntity<UnbookBookedRoomOutput> unbookBookedRoom(
            @PathVariable String bookingId) {

        UnbookBookedRoomInput input = UnbookBookedRoomInput.builder()
                .bookingId(bookingId)
                .build();

        return new ResponseEntity<>(hotelService.unbookBookedRoom(input), HttpStatus.OK);
    }

}
