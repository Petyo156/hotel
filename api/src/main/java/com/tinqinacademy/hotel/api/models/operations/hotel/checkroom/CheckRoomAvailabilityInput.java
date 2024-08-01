package com.tinqinacademy.hotel.api.models.operations.hotel.checkroom;

import com.tinqinacademy.hotel.api.models.base.OperationInput;
import com.tinqinacademy.hotel.api.models.base.OperationOutput;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class CheckRoomAvailabilityInput implements OperationInput {
    @FutureOrPresent
    private LocalDate startDate;

    @FutureOrPresent
    private LocalDate endDate;

    @NotNull(message = "bedSize can't be null!")
    private String bedSize;

    @NotNull(message = "bathroomType can't be null!")
    private String bathroomType;
}
