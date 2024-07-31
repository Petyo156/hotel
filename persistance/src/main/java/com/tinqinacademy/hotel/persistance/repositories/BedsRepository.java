package com.tinqinacademy.hotel.persistance.repositories;

import com.tinqinacademy.hotel.persistance.entities.Bed;
import com.tinqinacademy.hotel.persistance.more.BedSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BedsRepository extends JpaRepository<Bed, UUID> {
    List<Bed> findAllByBedSizeIn(List<BedSize> bedSize);


}
