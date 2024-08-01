package com.tinqinacademy.hotel.core.converters;

import com.tinqinacademy.hotel.api.models.operations.hotel.basicinfo.BasicInfoForRoomOutput;
import com.tinqinacademy.hotel.persistance.entities.Room;
import org.springframework.core.convert.converter.Converter;

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
