package com.tinqinacademy.hotel.api.models.operations.hotel.bookroom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.hotel.api.models.base.OperationInput;
import com.tinqinacademy.hotel.api.models.base.OperationOutput;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder=true)
@ToString
public class BookSpecifiedRoomInput implements OperationInput {
    @JsonIgnore
    private String roomId;

    private String userId;

    @FutureOrPresent
    private LocalDate startDate;

    @FutureOrPresent
    private LocalDate endDate;

    @NotNull(message = "firstName can't be null!")
    private String firstName;

    @NotNull(message = "lastName can't be null!")
    private String lastName;

    @NotNull(message = "phoneNo can't be null!")
    private String phoneNo;
}
