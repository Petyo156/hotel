package com.tinqinacademy.hotel.persistance.entities;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class GuestsToReservation implements Entity{
    private UUID id;
    private UUID guestFK;
    private UUID reservationFK;
}
