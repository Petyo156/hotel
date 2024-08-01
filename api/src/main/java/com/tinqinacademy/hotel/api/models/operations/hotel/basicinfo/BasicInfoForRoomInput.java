package com.tinqinacademy.hotel.api.models.operations.hotel.basicinfo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.hotel.api.models.base.OperationInput;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class BasicInfoForRoomInput implements OperationInput {
    @NotNull(message = "id can't be null!")
    private String roomId;
}
