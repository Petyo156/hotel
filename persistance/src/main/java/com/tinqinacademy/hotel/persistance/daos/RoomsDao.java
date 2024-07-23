package com.tinqinacademy.hotel.persistance.daos;


import com.tinqinacademy.hotel.persistance.BaseRepository;
import com.tinqinacademy.hotel.persistance.entities.Rooms;
import com.tinqinacademy.hotel.persistance.more.BathroomType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public class RoomsDao implements BaseRepository<Rooms, UUID> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RoomsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Rooms save(Rooms room) {
        String sql = "INSERT INTO rooms (id, floor, bathroom_type, price, room_number) " +
                "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                room.getId(),
                room.getFloor(),
                room.getBathroomType() != null ? room.getBathroomType().getCode() : null,
                room.getPrice(),
                room.getRoomNumber());
        return room;
    }

    @Override
    public Rooms findById(UUID id) {
        String sql = "SELECT * FROM rooms WHERE id = ?";
        return jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> Rooms.builder()
                        .id(UUID.fromString(rs.getString("id")))
                        .floor(rs.getInt("floor"))
                        .bathroomType(BathroomType.getByCode(rs.getString("bathroom_type")))
                        .price(rs.getFloat("price"))
                        .roomNumber(rs.getString("room_number"))
                        .build(),
                id);
    }

    @Override
    public void delete(UUID id) {
        String sql = "DELETE FROM rooms WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Rooms> findAll() {
        String sql = "SELECT * FROM rooms";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> Rooms.builder()
                        .id(UUID.fromString(rs.getString("id")))
                        .floor(rs.getInt("floor"))
                        .bathroomType(BathroomType.getByCode(rs.getString("bathroom_type")))
                        .price(rs.getFloat("price"))
                        .roomNumber(rs.getString("room_number"))
                        .build());
    }

    @Override
    public Long count() {
        String sql = "SELECT COUNT(*) FROM rooms";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    public void updateRoom(UUID id, Integer floor, String bathroomType, BigDecimal price) {
        String sql = "UPDATE rooms SET floor = ?, bathroom_type = ?, price = ? WHERE id = ?";
        jdbcTemplate.update(sql, floor, bathroomType, price, id);
    }

}
