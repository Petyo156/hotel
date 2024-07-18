package com.tinqinacademy.hotel.api.models.hotel.unbook.booked.room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@ToString
public class UnbookBookedRoomInput {
    //@NotNull(message = "id can't be null!")
    //@Size(min = 1, max = 5)
    @JsonIgnore
    private String bookingId;
}
