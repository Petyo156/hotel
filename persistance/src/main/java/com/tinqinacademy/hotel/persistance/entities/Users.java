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
public class Users implements Entity{
    private UUID id;
    private String username;
    private String password;
    private Date birthDate;
    private String phoneNumber;
    private String email;
}
