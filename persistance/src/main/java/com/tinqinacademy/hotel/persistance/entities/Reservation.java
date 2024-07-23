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
public class Reservation implements Entity{
    private UUID id;
    private Date startDate;
    private Date endDate;
    private Float price;
    private String roomID;
    private UUID userID;
}
