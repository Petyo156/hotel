package com.tinqinacademy.hotel.persistance.repositories;

import com.tinqinacademy.hotel.persistance.entities.Bed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BedsRepository extends JpaRepository<Bed, UUID> {



}
