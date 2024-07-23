package com.tinqinacademy.hotel.api.models.hotel.check.room;

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
public class CheckRoomAvailabilityOutput {
    private List<UUID> ids;
}
