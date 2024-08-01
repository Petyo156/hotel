package com.tinqinacademy.hotel.api.models.operations.system.registervisitor;

import com.tinqinacademy.hotel.api.models.base.OperationInput;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RegisterVisitorInput implements OperationInput {
    @NotNull(message = "roomNo can't be null!")
    private String roomNumber;

    @FutureOrPresent
    private LocalDate startDate;

    @FutureOrPresent
    private LocalDate endDate;

    private List<RegisterVisitorsDataInput> registerVisitorsDataInputList;
}
