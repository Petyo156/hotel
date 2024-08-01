package com.tinqinacademy.hotel.api.models.operations.system.adminpartialupdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.hotel.api.models.base.OperationInput;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@ToString
public class AdminPartialUpdateInput implements OperationInput {
    @JsonIgnore
    private UUID roomId;

    private String bedSize;

    private String bathroomType;

    @Min(1)
    private Integer floor;

    private String roomNumber;

    @PositiveOrZero
    private BigDecimal price;
}
