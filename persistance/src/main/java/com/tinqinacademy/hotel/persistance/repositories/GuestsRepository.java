package com.tinqinacademy.hotel.persistance.repositories;

import com.tinqinacademy.hotel.persistance.entities.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GuestsRepository extends JpaRepository<Guest, UUID> {
    Optional<Guest> findByFirstNameAndLastNameAndPhoneNo(String firstName, String lastName, String phoneNo);

}
