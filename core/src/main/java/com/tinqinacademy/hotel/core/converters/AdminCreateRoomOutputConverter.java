package com.tinqinacademy.hotel.core.converters;

import com.tinqinacademy.hotel.api.models.operations.system.admincreateroom.AdminCreateRoomInput;
import com.tinqinacademy.hotel.persistance.entities.Room;
import com.tinqinacademy.hotel.persistance.more.BathroomType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AdminCreateRoomOutputConverter implements Converter<AdminCreateRoomInput, Room.RoomBuilder> {
    @Override
    public Room.RoomBuilder convert(AdminCreateRoomInput input) {
        return Room.builder()
                .bathroomType(BathroomType.getByCode(input.getBathroomType()))
                .floor(input.getFloor())
                .price(input.getPrice())
                .roomNumber(input.getRoomNumber());
    }
}
