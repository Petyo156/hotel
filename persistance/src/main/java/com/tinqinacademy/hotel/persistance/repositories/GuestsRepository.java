package com.tinqinacademy.hotel.persistance.repositories;

import com.tinqinacademy.hotel.persistance.entities.Beds;
import com.tinqinacademy.hotel.persistance.entities.Guests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GuestsRepository extends JpaRepository<Guests, UUID> {
    Optional<Guests> findByFirstNameAndLastNameAndPhoneNo(String firstName, String lastName, String phoneNo);
}
