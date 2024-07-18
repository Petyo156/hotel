package com.tinqinacademy.hotel.api.models.hotel.book.room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder=true)
@ToString
public class BookSpecifiedRoomInput {
    @JsonIgnore
    private String roomId;

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
