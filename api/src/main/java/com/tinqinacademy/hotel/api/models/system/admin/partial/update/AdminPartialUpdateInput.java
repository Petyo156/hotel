package com.tinqinacademy.hotel.api.models.system.admin.partial.update;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class AdminPartialUpdateInput {
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

    @NotNull(message = "price can't be null!")
    @PositiveOrZero
    private BigDecimal price;
}
