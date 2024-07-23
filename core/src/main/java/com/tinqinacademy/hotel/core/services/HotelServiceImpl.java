package com.tinqinacademy.hotel.core.services;

import com.tinqinacademy.hotel.api.models.hotel.basic.info.BasicInfoForRoomInput;
import com.tinqinacademy.hotel.api.models.hotel.basic.info.BasicInfoForRoomOutput;
import com.tinqinacademy.hotel.api.models.hotel.book.room.BookSpecifiedRoomInput;
import com.tinqinacademy.hotel.api.models.hotel.book.room.BookSpecifiedRoomOutput;
import com.tinqinacademy.hotel.api.models.hotel.check.room.CheckRoomAvailabilityInput;

import com.tinqinacademy.hotel.api.models.hotel.check.room.CheckRoomAvailabilityOutput;
import com.tinqinacademy.hotel.api.models.hotel.unbook.booked.room.UnbookBookedRoomInput;
import com.tinqinacademy.hotel.api.models.hotel.unbook.booked.room.UnbookBookedRoomOutput;

import com.tinqinacademy.hotel.persistance.daos.BedsDao;
import com.tinqinacademy.hotel.persistance.entities.Beds;
import com.tinqinacademy.hotel.persistance.more.BathroomType;
import com.tinqinacademy.hotel.persistance.more.BedSize;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HotelServiceImpl implements HotelService {

    private final BedsDao bedsDao;

    @Autowired
    public HotelServiceImpl(BedsDao bedsDao) {
        this.bedsDao = bedsDao;
    }

    @Override
    public CheckRoomAvailabilityOutput checkRoomAvailability(CheckRoomAvailabilityInput input) {
        log.info("Start checkRoomAvailability input: {}", input);

        //logic
        List<UUID> availableRoomIds = bedsDao.findAll().stream()
                .filter(bed -> bedsDao.isRoomAvailable(bed.getId(), input.getStartDate(), input.getEndDate()))
                .map(Beds::getId)
                .collect(Collectors.toList());

        CheckRoomAvailabilityOutput output = CheckRoomAvailabilityOutput.builder()
                .ids(availableRoomIds)
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
