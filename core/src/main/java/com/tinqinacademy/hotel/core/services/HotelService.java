package com.tinqinacademy.hotel.core.services;


import com.tinqinacademy.hotel.api.models.hotel.basic.info.BasicInfoForRoomInput;
import com.tinqinacademy.hotel.api.models.hotel.basic.info.BasicInfoForRoomOutput;
import com.tinqinacademy.hotel.api.models.hotel.book.room.BookSpecifiedRoomInput;
import com.tinqinacademy.hotel.api.models.hotel.book.room.BookSpecifiedRoomOutput;
import com.tinqinacademy.hotel.api.models.hotel.check.room.CheckRoomAvailabilityInput;
import com.tinqinacademy.hotel.api.models.hotel.check.room.CheckRoomAvailabilityOutput;
import com.tinqinacademy.hotel.api.models.hotel.unbook.booked.room.UnbookBookedRoomInput;
import com.tinqinacademy.hotel.api.models.hotel.unbook.booked.room.UnbookBookedRoomOutput;

public interface HotelService {

    CheckRoomAvailabilityOutput checkRoomAvailability(CheckRoomAvailabilityInput input);

    BasicInfoForRoomOutput basicInfoForRoom(BasicInfoForRoomInput input);

    BookSpecifiedRoomOutput bookSpecifiedRoom(BookSpecifiedRoomInput input);

    UnbookBookedRoomOutput unbookBookedRoom(UnbookBookedRoomInput input);


}
