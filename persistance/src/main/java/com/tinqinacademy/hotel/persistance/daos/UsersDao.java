package com.tinqinacademy.hotel.persistance.daos;

import com.tinqinacademy.hotel.persistance.BaseRepository;
import com.tinqinacademy.hotel.persistance.entities.Users;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import com.tinqinacademy.hotel.persistance.BaseRepository;
import com.tinqinacademy.hotel.persistance.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class UsersDao implements BaseRepository<Users, UUID> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UsersDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Users save(Users user) {
        String sql = "INSERT INTO users (id, username, password, email) " +
                "VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail());
        return user;
    }

    @Override
    public Users findById(UUID id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> Users.builder()
                        .id(UUID.fromString(rs.getString("id")))
                        .username(rs.getString("username"))
                        .password(rs.getString("password"))
                        .email(rs.getString("email"))
                        .build(),
                id);
    }

    @Override
    public void delete(UUID id) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Users> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> Users.builder()
                        .id(UUID.fromString(rs.getString("id")))
                        .username(rs.getString("username"))
                        .password(rs.getString("password"))
                        .email(rs.getString("email"))
                        .build());
    }

    @Override
    public Long count() {
        String sql = "SELECT COUNT(*) FROM users";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }
}
