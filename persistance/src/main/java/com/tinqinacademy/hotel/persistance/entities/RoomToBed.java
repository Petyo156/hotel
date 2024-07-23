package com.tinqinacademy.hotel.persistance.entities;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RoomToBed implements Entity{
    private UUID bedFK;
    private UUID roomFK;
}
