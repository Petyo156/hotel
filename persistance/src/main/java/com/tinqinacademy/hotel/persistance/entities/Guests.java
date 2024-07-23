package com.tinqinacademy.hotel.persistance.entities;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Guests implements Entity{
    private UUID id;
    private String firstName;
    private String lastName;
    private Date birthdate;
    private String IdCardNumber;
    private String IdCardValidity;
    private String IdCardAuthority;
    private Date IdCardIssueDate;
}
