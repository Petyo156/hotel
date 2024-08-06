package com.tinqinacademy.hotel.restexport;

import com.tinqinacademy.hotel.api.models.apimapping.RestApiMapping;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@FeignClient(name = "hotelClient", url = "http://localhost:8080")
public interface HotelClient {
    @GetMapping(RestApiMapping.GET_checkAvailability_PATH)
    ResponseEntity<?> checkAvailability(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate,
            @RequestParam("bedSize") String bedSize,
            @RequestParam("bathroomType") String bathroomType);
}
