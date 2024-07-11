package com.tinqinacademy.hotel.api.models.system.delete.room;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class DeleteRoomInput {
    @NotNull(message = "id can't be null!")
    @Size(min = 1, max = 5, message = "id should be 1-5 chars long")
    private String id;
}
