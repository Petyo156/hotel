package com.tinqinacademy.hotel.persistance.entities;

import com.tinqinacademy.hotel.persistance.more.BedSize;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "beds")
public class Bed {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "bed_size", nullable = false)
    private BedSize bedSize;

    @ManyToMany(mappedBy = "beds")
    private List<Room> rooms;
}
