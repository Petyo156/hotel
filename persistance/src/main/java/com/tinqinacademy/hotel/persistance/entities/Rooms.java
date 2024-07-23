package com.tinqinacademy.hotel.persistance.entities;

import com.tinqinacademy.hotel.persistance.more.BathroomType;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Rooms implements Entity{
    private UUID id;
    private Integer floor;
    private BathroomType bathroomType;
    private Float price;
    private String roomNumber;
}
