package com.tinqinacademy.hotel.core.services;


import com.tinqinacademy.hotel.api.models.operations.hotel.basicinfo.BasicInfoForRoomInput;
import com.tinqinacademy.hotel.api.models.operations.hotel.basicinfo.BasicInfoForRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.hotel.bookroom.BookSpecifiedRoomInput;
import com.tinqinacademy.hotel.api.models.operations.hotel.bookroom.BookSpecifiedRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.hotel.checkroom.CheckRoomAvailabilityInput;
import com.tinqinacademy.hotel.api.models.operations.hotel.checkroom.CheckRoomAvailabilityOutput;
import com.tinqinacademy.hotel.api.models.operations.hotel.unbookbookedroom.UnbookBookedRoomInput;
import com.tinqinacademy.hotel.api.models.operations.hotel.unbookbookedroom.UnbookBookedRoomOutput;

public interface HotelService {

    CheckRoomAvailabilityOutput checkRoomAvailability(CheckRoomAvailabilityInput input);

    BasicInfoForRoomOutput basicInfoForRoom(BasicInfoForRoomInput input);

    BookSpecifiedRoomOutput bookSpecifiedRoom(BookSpecifiedRoomInput input);

    UnbookBookedRoomOutput unbookBookedRoom(UnbookBookedRoomInput input);


}
