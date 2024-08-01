package com.tinqinacademy.hotel.api.models.operations.system.adminpartialupdate;

import com.tinqinacademy.hotel.api.models.base.OperationInput;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class AdminPartialUpdateInput implements OperationInput {

    private String bedSize;

    private String bathroomType;

    @Min(1)
    private Integer floor;

    private String roomNumber;

    @PositiveOrZero
    private BigDecimal price;
}
