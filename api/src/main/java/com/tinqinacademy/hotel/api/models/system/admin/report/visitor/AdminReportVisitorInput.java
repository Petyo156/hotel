package com.tinqinacademy.hotel.api.models.system.admin.report.visitor;

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
public class AdminReportVisitorInput {
    @FutureOrPresent
    private LocalDate startDate;

    @FutureOrPresent
    private LocalDate endDate;

    @NotNull(message = "firstName can't be null!")
    @Size(min = 1, message = "firstName - atleast 1 char long")
    private String firstName;

    @NotNull(message = "lastName can't be null!")
    @Size(min = 1, message = "lastName - atleast 1 char long")
    private String lastName;

    @NotNull(message = "phoneNo can't be null!")
    @Size(min = 5, max = 5, message = "phoneNo - 5 chars long")
    private String phoneNo;

    @NotNull(message = "cardNoID can't be null!")
    @Size(min = 1, max = 5)
    private String cardNoID;

    @NotNull(message = "cardValidityID can't be null!")
    @Size(min = 1, max = 5)
    private String cardValidityID;

    @NotNull(message = "cardValidityID can't be null!")
    @Size(min = 1, max = 5)
    private String cardIssueAuthorityID;

    @FutureOrPresent
    private LocalDate cardIssueDateID;

    @NotNull(message = "roomNo can't be null!")
    @Size(min = 1, max = 5, message = "id should be 1-5 chars long")
    private String roomNo;
}
