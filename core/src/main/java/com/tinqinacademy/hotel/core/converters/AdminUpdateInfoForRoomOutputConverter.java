package com.tinqinacademy.hotel.core.converters;

import com.tinqinacademy.hotel.api.models.operations.system.adminupdateinfoforroom.AdminUpdateInfoForRoomInput;
import com.tinqinacademy.hotel.persistance.entities.Room;
import com.tinqinacademy.hotel.persistance.more.BathroomType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class AdminUpdateInfoForRoomOutputConverter implements Converter<AdminUpdateInfoForRoomInput, Room.RoomBuilder> {
    @Override
    public Room.RoomBuilder convert(AdminUpdateInfoForRoomInput input) {
        return Room.builder()
                .id(UUID.fromString(input.getId()))
                .roomNumber(input.getRoomNo())
                .price(input.getPrice())
                .floor(input.getFloor())
                .bathroomType(BathroomType.getByCode(input.getBathroomType()));
    }
}
