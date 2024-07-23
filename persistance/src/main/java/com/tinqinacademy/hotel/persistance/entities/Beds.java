package com.tinqinacademy.hotel.persistance.entities;

import com.tinqinacademy.hotel.persistance.more.BedSize;
import lombok.*;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Beds implements Entity{
    private UUID id;
    private BedSize bedSize;
    private Integer bedCount;
}
