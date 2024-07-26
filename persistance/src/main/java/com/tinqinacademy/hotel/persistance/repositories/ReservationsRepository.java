package com.tinqinacademy.hotel.persistance.repositories;

import com.tinqinacademy.hotel.persistance.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationsRepository extends JpaRepository<Reservation, UUID> {
    List<Reservation> findAllByRoomIdAndStartDateBeforeAndEndDateAfter(UUID roomId, LocalDate endDate, LocalDate startDate);
    List<Reservation> findByRoomId(UUID roomId);
}
