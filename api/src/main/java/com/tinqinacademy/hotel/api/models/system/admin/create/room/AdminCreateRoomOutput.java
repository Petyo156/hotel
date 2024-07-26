package com.tinqinacademy.hotel.api.models.system.admin.create.room;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class AdminCreateRoomOutput {
    private UUID id;
}
