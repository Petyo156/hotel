package com.tinqinacademy.hotel.persistance.daos;

import com.tinqinacademy.hotel.persistance.BaseRepository;
import com.tinqinacademy.hotel.persistance.entities.Guests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class GuestsDao implements BaseRepository<Guests, UUID> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GuestsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Guests save(Guests guest) {
        String sql = """
            INSERT INTO guests (
                id, first_name, last_name, birthdate, 
                id_card_number, id_card_validity, 
                id_card_authority, id_card_issue_date
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;

        jdbcTemplate.update(sql,
                guest.getId().toString(),
                guest.getFirstName(),
                guest.getLastName(),
                guest.getBirthdate(),
                guest.getIdCardNumber(),
                guest.getIdCardValidity(),
                guest.getIdCardAuthority(),
                guest.getIdCardIssueDate()
        );

        return guest;
    }

    @Override
    public Guests findById(UUID id) {
        String sql = "SELECT * FROM guests WHERE id = ?";

        return jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> Guests.builder()
                        .id(UUID.fromString(rs.getString("id")))
                        .firstName(rs.getString("first_name"))
                        .lastName(rs.getString("last_name"))
                        .birthdate(rs.getDate("birthdate"))
                        .IdCardNumber(rs.getString("id_card_number"))
                        .IdCardValidity(rs.getString("id_card_validity"))
                        .IdCardAuthority(rs.getString("id_card_authority"))
                        .IdCardIssueDate(rs.getDate("id_card_issue_date"))
                        .build(),
                id.toString());
    }

    @Override
    public void delete(UUID id) {
        String sql = "DELETE FROM guests WHERE id = ?";
        jdbcTemplate.update(sql, id.toString());
    }

    @Override
    public List<Guests> findAll() {
        String sql = "SELECT * FROM guests";

        return jdbcTemplate.query(sql, (rs, rowNum) -> Guests.builder()
                .id(UUID.fromString(rs.getString("id")))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .birthdate(rs.getDate("birthdate"))
                .IdCardNumber(rs.getString("id_card_number"))
                .IdCardValidity(rs.getString("id_card_validity"))
                .IdCardAuthority(rs.getString("id_card_authority"))
                .IdCardIssueDate(rs.getDate("id_card_issue_date"))
                .build());
    }


    @Override
    public Long count() {
        String sql = "SELECT COUNT(*) FROM guests";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }
}
