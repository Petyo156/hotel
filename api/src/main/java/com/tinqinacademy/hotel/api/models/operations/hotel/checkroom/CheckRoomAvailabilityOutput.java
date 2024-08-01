package com.tinqinacademy.hotel.api.models.operations.hotel.checkroom;

import com.tinqinacademy.hotel.api.models.base.OperationOutput;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class CheckRoomAvailabilityOutput implements OperationOutput {
    private List<UUID> ids;
}
