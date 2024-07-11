package com.tinqinacademy.hotel.api.models.hotel.check.room;

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
public class CheckRoomAvailabilityInput {
    @FutureOrPresent
    private LocalDate startDate;

    @FutureOrPresent
    private LocalDate endDate;

    @NotNull(message = "bedCount can't be null!")
    @Min(1)
    @Max(10)
    private Integer bedCount;

    @NotNull(message = "bedSize can't be null!")
    private String bedSize;

    @NotNull(message = "bathroomType can't be null!")
    private String bathroomType;
}
