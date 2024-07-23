package com.tinqinacademy.hotel.persistance;

import com.tinqinacademy.hotel.persistance.entities.Entity;

import java.util.List;
import java.util.UUID;

public interface BaseRepository<T extends Entity, ID> {
    T save(T entity);
    T findById(UUID id);
    void delete(UUID id);
    List<T> findAll();
    Long count();
}