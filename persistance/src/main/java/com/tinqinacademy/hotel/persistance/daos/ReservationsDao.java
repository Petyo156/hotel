package com.tinqinacademy.hotel.persistance.daos;

import com.tinqinacademy.hotel.persistance.BaseRepository;
import com.tinqinacademy.hotel.persistance.entities.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class ReservationsDao implements BaseRepository<Reservation, UUID> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservation save(Reservation reservation) {
        String sql = "INSERT INTO reservations (id, start_date, end_date, price, room_id, user_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                reservation.getId(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getPrice(),
                reservation.getRoomID(),
                reservation.getUserID());
        return reservation;
    }

    @Override
    public Reservation findById(UUID id) {
        String sql = "SELECT * FROM reservations WHERE id = ?";
        return jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> Reservation.builder()
                        .id(UUID.fromString(rs.getString("id")))
                        .startDate(rs.getDate("start_date"))
                        .endDate(rs.getDate("end_date"))
                        .price(rs.getFloat("price"))
                        .roomID(rs.getString("room_id"))
                        .userID(UUID.fromString(rs.getString("user_id")))
                        .build(),
                id);
    }

    @Override
    public void delete(UUID id) {
        String sql = "DELETE FROM reservations WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT * FROM reservations";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> Reservation.builder()
                        .id(UUID.fromString(rs.getString("id")))
                        .startDate(rs.getDate("start_date"))
                        .endDate(rs.getDate("end_date"))
                        .price(rs.getFloat("price"))
                        .roomID(rs.getString("room_id"))
                        .userID(UUID.fromString(rs.getString("user_id")))
                        .build());
    }

    @Override
    public Long count() {
        String sql = "SELECT COUNT(*) FROM reservations";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }
}
