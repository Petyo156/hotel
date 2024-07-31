package com.tinqinacademy.hotel.persistance.repositories;

import com.tinqinacademy.hotel.persistance.entities.Room;
import com.tinqinacademy.hotel.persistance.more.BathroomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoomsRepository extends JpaRepository<Room, UUID> {
    Optional<Room> findByRoomNumber(String roomNumber);
    @Query(value = """
                        SELECT r.* 
                        FROM rooms r 
                        LEFT JOIN reservations res ON r.id = res.room_id 
                            AND res.start_date <= :endDate 
                            AND res.end_date >= :startDate 
                        WHERE res.room_id IS NULL;
            """, nativeQuery = true)
    List<Room> findAvailableRooms(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
