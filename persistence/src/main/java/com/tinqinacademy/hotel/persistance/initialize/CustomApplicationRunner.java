package com.tinqinacademy.hotel.persistance.initialize;

import com.tinqinacademy.hotel.persistance.entities.Bed;
import com.tinqinacademy.hotel.persistance.entities.Room;
import com.tinqinacademy.hotel.persistance.more.BathroomType;
import com.tinqinacademy.hotel.persistance.more.BedSize;
import com.tinqinacademy.hotel.persistance.repositories.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

@Component
@Log4j2
public class CustomApplicationRunner implements ApplicationRunner {
    //We’ll want to use an ApplicationRunner if we need to create
    // some global startup logic with access to complex command-line arguments.

    private BedsRepository bedsRepository;
    private RoomsRepository roomsRepository;

    @Autowired
    public CustomApplicationRunner(BedsRepository bedsRepository, RoomsRepository roomsRepository) {
        this.bedsRepository = bedsRepository;
        this.roomsRepository = roomsRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (bedsRepository.count() == 0) {
            bedsRepository.save(Bed.builder()
                    .id(UUID.randomUUID())
                    .bedSize(BedSize.SINGLE)
                    .build());
            bedsRepository.save(Bed.builder()
                    .id(UUID.randomUUID())
                    .bedSize(BedSize.DOUBLE)
                    .build());
            bedsRepository.save(Bed.builder()
                    .id(UUID.randomUUID())
                    .bedSize(BedSize.SMALLDOUBLE)
                    .build());
            bedsRepository.save(Bed.builder()
                    .id(UUID.randomUUID())
                    .bedSize(BedSize.KINGSIZE)
                    .build());
            bedsRepository.save(Bed.builder()
                    .id(UUID.randomUUID())
                    .bedSize(BedSize.QUEENSIZE)
                    .build());
        }

        if (roomsRepository.count() == 0) {
            roomsRepository.save(Room.builder()
                    .id(UUID.randomUUID())
                    .bathroomType(BathroomType.PRIVATE)
                    .price(BigDecimal.ONE)
                    .floor(5)
                    .beds(bedsRepository.findAll())
                    .roomNumber("100")
                    .build());
            roomsRepository.save(Room.builder()
                    .id(UUID.randomUUID())
                    .bathroomType(BathroomType.PRIVATE)
                    .price(BigDecimal.ONE)
                    .floor(5)
                    .beds(bedsRepository.findAll())
                    .roomNumber("200")
                    .build());
            roomsRepository.save(Room.builder()
                    .id(UUID.randomUUID())
                    .bathroomType(BathroomType.PRIVATE)
                    .price(BigDecimal.ONE)
                    .floor(5)
                    .beds(bedsRepository.findAll())
                    .roomNumber("300")
                    .build());
        }


        log.info("Fully working!!!");
    }
}