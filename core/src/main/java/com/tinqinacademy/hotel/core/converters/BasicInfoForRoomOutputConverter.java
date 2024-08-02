package com.tinqinacademy.hotel.core.converters;

import com.tinqinacademy.hotel.api.models.operations.hotel.basicinfo.BasicInfoForRoomOutput;
import com.tinqinacademy.hotel.persistance.entities.Room;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BasicInfoForRoomOutputConverter implements Converter<Room, BasicInfoForRoomOutput.BasicInfoForRoomOutputBuilder> {
    @Override
    public BasicInfoForRoomOutput.BasicInfoForRoomOutputBuilder convert(Room room) {
        return BasicInfoForRoomOutput.builder()
                .roomId(room.getId())
                .floor(room.getFloor())
                .price(room.getPrice())
                .bathroomType(String.valueOf(room.getBathroomType()));
    }
}
