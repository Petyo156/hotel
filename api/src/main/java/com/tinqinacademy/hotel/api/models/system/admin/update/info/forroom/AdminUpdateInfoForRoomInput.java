package com.tinqinacademy.hotel.api.models.system.admin.update.info.forroom;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class AdminUpdateInfoForRoomInput {

    @NotNull(message = "bedCount can't be null!")
    @Size(min = 1, max = 10, message = "bedcount should be 1-10")
    private Integer bedCount;

    @NotNull(message = "bedSize can't be null!")
    private String bedSize;

    @NotNull(message = "bathroomType can't be null!")
    private String bathroomType;

    @NotNull(message = "floor can't be null!")
    @Positive
    private Integer floor;

    @NotNull(message = "roomNo can't be null!")
    @Size(min = 3, max = 3, message = "roomNo - 3 chars long")
    private String roomNo;

    @NotNull(message = "price can't be null!")
    @PositiveOrZero
    private BigDecimal price;
}
