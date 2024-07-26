package com.tinqinacademy.hotel.api.models.hotel.basic.info;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class BasicInfoForRoomInput {
    @NotNull(message = "id can't be null!")
    private UUID roomId;
}
