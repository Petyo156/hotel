package com.tinqinacademy.hotel.persistance.repositories;

import com.tinqinacademy.hotel.persistance.entities.Beds;
import com.tinqinacademy.hotel.persistance.entities.Reservations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationsRepository extends JpaRepository<Reservations, UUID> {
    List<Reservations> findAllByRoomIdAndStartDateBeforeAndEndDateAfter(UUID roomId, LocalDate endDate, LocalDate startDate);
    List<Reservations> findByRoomId(UUID roomId);
//    List<Reservations> findByCardNoId(String cardNoId);
}
