package com.tinqinacademy.hotel.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.hotel.api.models.hotel.basic.info.BasicInfoForRoomInput;
import com.tinqinacademy.hotel.api.models.hotel.basic.info.BasicInfoForRoomOutput;
import com.tinqinacademy.hotel.api.models.hotel.book.room.BookSpecifiedRoomInput;
import com.tinqinacademy.hotel.api.models.hotel.book.room.BookSpecifiedRoomOutput;
import com.tinqinacademy.hotel.api.models.hotel.check.room.CheckRoomAvailabilityInput;
import com.tinqinacademy.hotel.api.models.hotel.check.room.CheckRoomAvailabilityOutput;
import com.tinqinacademy.hotel.api.models.hotel.unbook.booked.room.UnbookBookedRoomInput;
import com.tinqinacademy.hotel.api.models.hotel.unbook.booked.room.UnbookBookedRoomOutput;
import com.tinqinacademy.hotel.core.services.HotelService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/hotel")
public class HotelController {
    //only in post put patch POJOS are needed validations

    private HotelService hotelService;
    private ObjectMapper objectMapper;

    @Autowired
    public HotelController(HotelService hotelService, ObjectMapper objectMapper) {
        this.hotelService = hotelService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/rooms")
    public ResponseEntity<CheckRoomAvailabilityOutput> checkAvailability(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate,
            @RequestParam("bedCount") Integer bedCount,
            @RequestParam("bedSize") String bedSize,
            @RequestParam("bathroomType") String bathroomType) {

        CheckRoomAvailabilityInput input = CheckRoomAvailabilityInput.builder()
                .endDate(endDate)
                .startDate(startDate)
                .bathroomType(bathroomType)
                .bedCount(bedCount)
                .bedSize(bedSize)
                .build();

        return new ResponseEntity<>(hotelService.checkRoomAvailability(input), HttpStatus.OK);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<BasicInfoForRoomOutput> basicInfoForRoom(
            @PathVariable("roomId")
            UUID roomId) {

        BasicInfoForRoomInput input = BasicInfoForRoomInput.builder()
                .roomId(roomId)
                .build();

        return new ResponseEntity<>(hotelService.basicInfoForRoom(input), HttpStatus.OK);
    }

    @PostMapping("/{roomId}")
    public ResponseEntity<BookSpecifiedRoomOutput> bookSpecifiedRoom(
            @Valid @RequestBody BookSpecifiedRoomInput input,
            @PathVariable("roomId") UUID roomId) {

        BookSpecifiedRoomInput bookSpecifiedRoomInput = input.toBuilder()
                .roomId(roomId)
                .build();

        return new ResponseEntity<>(hotelService.bookSpecifiedRoom(bookSpecifiedRoomInput), HttpStatus.OK);
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<UnbookBookedRoomOutput> unbookBookedRoom(
            @PathVariable String bookingId) {

        UnbookBookedRoomInput input = UnbookBookedRoomInput.builder()
                .bookingId(bookingId)
                .build();

        return new ResponseEntity<>(hotelService.unbookBookedRoom(input), HttpStatus.OK);
    }

}
