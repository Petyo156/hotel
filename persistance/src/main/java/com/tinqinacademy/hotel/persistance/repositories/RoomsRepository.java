package com.tinqinacademy.hotel.persistance.repositories;

import com.tinqinacademy.hotel.persistance.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoomsRepository extends JpaRepository<Room, UUID> {
    Room getRoomIdByRoomNumber(String roomNumber);
    Room getRoomById(UUID id);

}
