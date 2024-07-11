package com.tinqinacademy.hotel.api.models.system.admin.create.room;


import jakarta.validation.constraints.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class AdminCreateRoomInput {
    @NotNull(message = "bedCount can't be null!")
    @Min(1)
    @Max(10)
    private Integer bedCount;

    @NotNull(message = "bedSize can't be null!")
    private String bedSize;

    @NotNull(message = "bathroomType can't be null!")
    private String bathroomType;

    @NotNull(message = "floor can't be null!")
    @Min(1)
    private Integer floor;

    @NotNull(message = "roomNo can't be null!")
    @Size(min = 3, max = 3, message = "roomNo - 3 chars long")
    private String roomNo;

    @NotNull(message = "roomNo can't be null!")
    @PositiveOrZero
    private Double price;
}
