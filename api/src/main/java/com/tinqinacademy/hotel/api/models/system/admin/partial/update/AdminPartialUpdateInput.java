package com.tinqinacademy.hotel.api.models.system.admin.partial.update;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class AdminPartialUpdateInput {

    private String bedSize;

    private String bathroomType;

    @Min(1)
    private Integer floor;

    private String roomNumber;

    @PositiveOrZero
    private BigDecimal price;
}
