package com.tinqinacademy.hotel.api.models.operations.system.adminupdateinfoforroom;

import com.tinqinacademy.hotel.api.models.base.OperationOutput;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class AdminUpdateInfoForRoomOutput implements OperationOutput {
    private UUID id;
}
