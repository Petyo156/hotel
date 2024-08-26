package com.tinqinacademy.hotel.restexport;

import com.tinqinacademy.hotel.api.models.apimapping.RestApiMappingHotel;
import com.tinqinacademy.hotel.api.models.operations.hotel.basicinfo.BasicInfoForRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.hotel.bookroom.BookSpecifiedRoomInput;
import com.tinqinacademy.hotel.api.models.operations.hotel.bookroom.BookSpecifiedRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.hotel.checkroom.CheckRoomAvailabilityOutput;
import com.tinqinacademy.hotel.api.models.operations.hotel.unbookbookedroom.UnbookBookedRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.system.admincreateroom.AdminCreateRoomInput;
import com.tinqinacademy.hotel.api.models.operations.system.admincreateroom.AdminCreateRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.system.adminpartialupdate.AdminPartialUpdateInput;
import com.tinqinacademy.hotel.api.models.operations.system.adminpartialupdate.AdminPartialUpdateOutput;
import com.tinqinacademy.hotel.api.models.operations.system.adminreportvisitor.AdminReportVisitorOutput;
import com.tinqinacademy.hotel.api.models.operations.system.adminupdateinfoforroom.AdminUpdateInfoForRoomInput;
import com.tinqinacademy.hotel.api.models.operations.system.adminupdateinfoforroom.AdminUpdateInfoForRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.system.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.system.registervisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.api.models.operations.system.registervisitor.RegisterVisitorOutput;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@FeignClient(name = "HotelClient", url = "http://localhost:8080")
public interface HotelClient {
    //hotel
    @GetMapping(RestApiMappingHotel.GET_checkAvailability_PATH)
    CheckRoomAvailabilityOutput checkAvailability(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate,
            @RequestParam("bedSize") String bedSize,
            @RequestParam("bathroomType") String bathroomType);

    @GetMapping(RestApiMappingHotel.GET_basicInfoForRoom_PATH)
    BasicInfoForRoomOutput basicInfoForRoom(
            @PathVariable("roomId") String roomId);

    @PostMapping(RestApiMappingHotel.POST_bookSpecifiedRoom_PATH)
    BookSpecifiedRoomOutput bookSpecifiedRoom(
            @RequestBody BookSpecifiedRoomInput bookSpecifiedRoomInput,
            @PathVariable("roomId") String roomId);

    @DeleteMapping(RestApiMappingHotel.DELETE_unbookBookedRoom_PATH)
    UnbookBookedRoomOutput unbookBookedRoom(
            @PathVariable String bookingId);

    //system

    @PostMapping(RestApiMappingHotel.POST_registerVisitor_PATH)
    RegisterVisitorOutput registerVisitor(
            @RequestBody RegisterVisitorInput input);

    @GetMapping(RestApiMappingHotel.GET_adminReportVisitor_PATH)
    AdminReportVisitorOutput adminReportVisitor(
            @RequestParam("visitorsData") List<String> visitorsData,
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("phoneNo") String phoneNo,
            @RequestParam("cardNoID") String cardNoID,
            @RequestParam("cardValidityID") String cardValidityID,
            @RequestParam("cardIssueAuthorityID") String cardIssueAuthorityID,
            @RequestParam("cardIssueDateID") LocalDate cardIssueDateID);

    @PostMapping(RestApiMappingHotel.POST_adminCreateRoom_PATH)
    AdminCreateRoomOutput adminCreateRoom(
            @RequestBody AdminCreateRoomInput input);

    @PutMapping(RestApiMappingHotel.PUT_adminUpdateInfoForRoom_PATH)
    AdminUpdateInfoForRoomOutput adminUpdateInfoForRoom(
            @RequestBody AdminUpdateInfoForRoomInput adminUpdateInfoForRoomInput,
            @PathVariable("id") String id);

    @PatchMapping(value = RestApiMappingHotel.PATCH_adminPartialUpdate_PATH, consumes = "application/json-patch+json")
    AdminPartialUpdateOutput adminPartialUpdate(
            @RequestBody AdminPartialUpdateInput adminPartialUpdateInput,
            @PathVariable("id") String id);

    @DeleteMapping(RestApiMappingHotel.DELETE_deleteRoom_PATH)
    DeleteRoomOutput deleteRoom(
            @PathVariable("id") String id);
}
