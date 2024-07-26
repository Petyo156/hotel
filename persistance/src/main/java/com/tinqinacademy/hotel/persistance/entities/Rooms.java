package com.tinqinacademy.hotel.persistance.entities;

import com.tinqinacademy.hotel.persistance.more.BathroomType;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "rooms")
public class Rooms {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "floor", nullable = false)
    private Integer floor;

    @Column(name = "bathroom_type", nullable = false)
    private BathroomType bathroomType;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "room_number", nullable = false, unique = true)
    private String roomNumber;

    @ManyToMany
    @JoinTable(
            name = "room_beds",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "bed_id")
    )
    private List<Beds> beds;
}
