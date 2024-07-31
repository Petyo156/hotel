package com.tinqinacademy.hotel.api.models.system.register.visitor;

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
@Builder
@ToString
public class RegisterVisitorInput {
    @NotNull(message = "roomNo can't be null!")
    private String roomNumber;

    @FutureOrPresent
    private LocalDate startDate;

    @FutureOrPresent
    private LocalDate endDate;

    private List<RegisterVisitorsDataInput> registerVisitorsDataInputList;
}
