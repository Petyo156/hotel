package com.tinqinacademy.hotel.persistance.daos;

import com.tinqinacademy.hotel.persistance.BaseRepository;
import com.tinqinacademy.hotel.persistance.entities.Beds;
import com.tinqinacademy.hotel.persistance.more.BedSize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public class BedsDao implements BaseRepository<Beds, UUID> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BedsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Beds save(Beds bed) {
        String sql = "INSERT INTO beds (id, bedsize, bed_count) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, bed.getId(), bed.getBedSize().name(), bed.getBedCount());
        return bed;
    }

    @Override
    public Beds findById(UUID id) {
        String sql = "SELECT * FROM beds WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> Beds.builder()
                .id(UUID.fromString(rs.getString("id")))
                .bedSize(BedSize.getByCode(rs.getString("bedsize")))
                .build(), id.toString());
    }

    @Override
    public void delete(UUID id) {
        String sql = "DELETE FROM beds WHERE id = ?";
        jdbcTemplate.update(sql, id.toString());
    }

    @Override
    public List<Beds> findAll() {
        String sql = "SELECT * FROM beds";
        return jdbcTemplate.query(sql, (rs, rowNum) -> Beds.builder()
                .id(UUID.fromString(rs.getString("id")))
                .bedSize(BedSize.getByCode(rs.getString("bedsize")))
                .build());
    }

    @Override
    public Long count() {
        String sql = "SELECT COUNT(*) FROM beds";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    //logic
    public boolean isRoomAvailable(UUID roomId, LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT COUNT(*) FROM reservation WHERE room_id = ? AND (start_date < ? AND end_date > ?)";
        int count = jdbcTemplate.queryForObject(sql, new Object[]{roomId.toString(), endDate, startDate}, Integer.class);
        return count == 0;
    }
}
