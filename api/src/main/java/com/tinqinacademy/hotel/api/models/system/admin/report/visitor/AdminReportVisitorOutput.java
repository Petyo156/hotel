package com.tinqinacademy.hotel.api.models.system.admin.report.visitor;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class AdminReportVisitorOutput {
    private List<String> visitorsData;
    private LocalDate startDate;
    private LocalDate endDate;
    private String firstName;
    private String lastName;
    private String phoneNo;
    private String cardNoID;
    private String cardValidityID;
    private String cardIssueAuthorityID;
    private LocalDate cardIssueDateID;
}
