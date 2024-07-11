package com.tinqinacademy.hotel.core.services;

import com.tinqinacademy.hotel.api.models.hotel.basic.info.BasicInfoForRoomInput;
import com.tinqinacademy.hotel.api.models.hotel.basic.info.BasicInfoForRoomOutput;
import com.tinqinacademy.hotel.api.models.hotel.book.room.BookSpecifiedRoomInput;
import com.tinqinacademy.hotel.api.models.hotel.book.room.BookSpecifiedRoomOutput;
import com.tinqinacademy.hotel.api.models.hotel.check.room.CheckRoomAvailabilityInput;

import com.tinqinacademy.hotel.api.models.hotel.check.room.CheckRoomAvailabilityOutput;
import com.tinqinacademy.hotel.api.models.hotel.unbook.booked.room.UnbookBookedRoomInput;
import com.tinqinacademy.hotel.api.models.hotel.unbook.booked.room.UnbookBookedRoomOutput;
import com.tinqinacademy.hotel.api.models.more.BathroomType;
import com.tinqinacademy.hotel.api.models.more.BedSize;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class HotelServiceImpl implements HotelService {

    @Override
    public CheckRoomAvailabilityOutput checkRoomAvailability(CheckRoomAvailabilityInput input) {
        log.info("Start checkRoomAvailability input: {}", input);

        CheckRoomAvailabilityOutput output = CheckRoomAvailabilityOutput.builder()
                .ids(new ArrayList<>())
                .build();

        log.info("End checkRoomAvailability output: {}", output);
        return output;
    }

    @Override
    public BasicInfoForRoomOutput basicInfoForRoom(BasicInfoForRoomInput input) {
        log.info("Start basicInfoForRoom input: {}", input);

        BasicInfoForRoomOutput output = BasicInfoForRoomOutput.builder()
                .roomId(input.getRoomId())
                .floor(5)
                .price(BigDecimal.TWO)
                .bedSize(BedSize.KINGSIZE.toString())
                .bathroomType(BathroomType.PRIVATE.toString())
                .bedCount(5)
                .datesOccupied(List.of(LocalDate.of(2020, 1, 8)))
                .build();

        log.info("End basicInfoForRoom output: {}", output);
        return output;
    }

    @Override
    public BookSpecifiedRoomOutput bookSpecifiedRoom(BookSpecifiedRoomInput input) {
        log.info("Start bookSpecifiedRoom input: {}", input);

        BookSpecifiedRoomOutput output = BookSpecifiedRoomOutput.builder()
                .build();

        log.info("End bookSpecifiedRoom output: {}", output);
        return output;
    }

    @Override
    public UnbookBookedRoomOutput unbookBookedRoom(UnbookBookedRoomInput input) {
        log.info("Start unbookBookedRoom input: {}", input);

        UnbookBookedRoomOutput output = UnbookBookedRoomOutput.builder()
                .build();

        log.info("End unbookBookedRoom output: {}", output);
        return output;
    }

}
