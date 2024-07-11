package com.tinqinacademy.hotel.api.models.hotel.check.room;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class CheckRoomAvailabilityOutput {
    private List<String> ids;
}
