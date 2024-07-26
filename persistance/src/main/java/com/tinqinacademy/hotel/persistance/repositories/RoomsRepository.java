package com.tinqinacademy.hotel.persistance.repositories;

import com.tinqinacademy.hotel.persistance.entities.Beds;
import com.tinqinacademy.hotel.persistance.entities.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoomsRepository extends JpaRepository<Rooms, UUID> {
    Rooms getRoomsIdByRoomNumber(String roomNumber);
    Rooms getRoomsById(UUID id);
}
