package com.tinqinacademy.hotel.persistance.repositories;

import com.tinqinacademy.hotel.persistance.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<User, UUID> {
    User getByEmail(String email);
}
