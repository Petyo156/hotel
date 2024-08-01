package com.tinqinacademy.hotel.api.models.operations.hotel.unbookbookedroom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.hotel.api.models.base.OperationInput;
import com.tinqinacademy.hotel.api.models.base.OperationOutput;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@ToString
public class UnbookBookedRoomInput implements OperationInput {
    //@NotNull(message = "id can't be null!")
    //@Size(min = 1, max = 5)
    @JsonIgnore
    private String bookingId;
}
