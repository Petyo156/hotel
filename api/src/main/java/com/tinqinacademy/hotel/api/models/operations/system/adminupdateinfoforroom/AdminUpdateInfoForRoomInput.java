package com.tinqinacademy.hotel.api.models.operations.system.adminupdateinfoforroom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.hotel.api.models.base.OperationInput;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@ToString
public class AdminUpdateInfoForRoomInput implements OperationInput {
    @JsonIgnore
    private String id;

    @NotNull(message = "bedSize can't be null!")
    private List<String> bedSizes;

    @NotNull(message = "bathroomType can't be null!")
    private String bathroomType;

    @NotNull(message = "floor can't be null!")
    @Positive
    private Integer floor;

    @NotNull(message = "roomNo can't be null!")
    private String roomNo;

    @NotNull(message = "price can't be null!")
    @PositiveOrZero
    private BigDecimal price;
}
