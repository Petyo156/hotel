package com.tinqinacademy.hotel.api.models.hotel.basic.info;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class BasicInfoForRoomInput {
    @NotNull(message = "id can't be null!")
    private String roomId;
}
