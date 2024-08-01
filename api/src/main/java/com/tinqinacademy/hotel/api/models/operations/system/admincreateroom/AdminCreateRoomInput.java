package com.tinqinacademy.hotel.api.models.operations.system.admincreateroom;


import com.tinqinacademy.hotel.api.models.base.OperationInput;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class AdminCreateRoomInput implements OperationInput {

    @NotNull(message = "bedSize can't be null!")
    private List<String> bedSizes;

    @NotNull(message = "bathroomType can't be null!")
    private String bathroomType;

    @NotNull(message = "floor can't be null!")
    @Min(1)
    private Integer floor;

    @NotNull(message = "roomNo can't be null!")
    @Size(min = 3, max = 3, message = "roomNo - 3 chars long")
    private String roomNumber;

    @NotNull(message = "roomNo can't be null!")
    @PositiveOrZero
    private BigDecimal price;
}
