package com.tinqinacademy.hotel.api.models.operations.system.admincreateroom;

import com.tinqinacademy.hotel.api.models.base.OperationOutput;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class AdminCreateRoomOutput implements OperationOutput {
    private UUID id;
}
