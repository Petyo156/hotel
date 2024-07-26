package com.tinqinacademy.hotel.persistance.initialize;

import com.tinqinacademy.hotel.persistance.entities.Beds;
import com.tinqinacademy.hotel.persistance.entities.Guests;
import com.tinqinacademy.hotel.persistance.entities.Rooms;
import com.tinqinacademy.hotel.persistance.entities.Users;
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
import java.util.List;
import java.util.UUID;

@Component
@Log4j2
public class CustomApplicationRunner implements ApplicationRunner {
    //We’ll want to use an ApplicationRunner if we need to create
    // some global startup logic with access to complex command-line arguments.

    private BedsRepository bedsRepository;
    private UsersRepository usersRepository;
    private RoomsRepository roomsRepository;

    @Autowired
    public CustomApplicationRunner(BedsRepository bedsRepository, UsersRepository usersRepository, RoomsRepository roomsRepository) {
        this.bedsRepository = bedsRepository;
        this.usersRepository = usersRepository;
        this.roomsRepository = roomsRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (bedsRepository.count() == 0) {
            bedsRepository.save(Beds.builder()
                    .id(UUID.randomUUID())
                    .bedSize(BedSize.DOUBLE)
                    .build());
            bedsRepository.save(Beds.builder()
                    .id(UUID.randomUUID())
                    .bedSize(BedSize.SINGLE)
                    .build());
            bedsRepository.save(Beds.builder()
                    .id(UUID.randomUUID())
                    .bedSize(BedSize.KINGSIZE)
                    .build());
        }

        if (usersRepository.count() == 0) {
            usersRepository.save(Users.builder()
                    .id(UUID.randomUUID())
                    .birthDate(Date.valueOf("2030-12-12"))
                    .email("lol1@abv.bg")
                    .password("password1")
                    .username("user1")
                    .phoneNumber("12345")
                    .build());
            usersRepository.save(Users.builder()
                    .id(UUID.randomUUID())
                    .birthDate(Date.valueOf("2031-12-12"))
                    .email("lol2@abv.bg")
                    .password("password2")
                    .username("user2")
                    .phoneNumber("12345")
                    .build());
            usersRepository.save(Users.builder()
                    .id(UUID.randomUUID())
                    .birthDate(Date.valueOf("2032-12-12"))
                    .email("lol3@abv.bg")
                    .password("password3")
                    .username("user3")
                    .phoneNumber("12345")
                    .build());
        }

        if (roomsRepository.count() == 0) {
            roomsRepository.save(Rooms.builder()
                    .id(UUID.randomUUID())
                    .bathroomType(BathroomType.PRIVATE)
                    .price(BigDecimal.ONE)
                    .floor(5)
                    .beds(bedsRepository.findAll())
                    .roomNumber("100")
                    .build());
            roomsRepository.save(Rooms.builder()
                    .id(UUID.randomUUID())
                    .bathroomType(BathroomType.PRIVATE)
                    .price(BigDecimal.ONE)
                    .floor(5)
                    .beds(bedsRepository.findAll())
                    .roomNumber("200")
                    .build());
            roomsRepository.save(Rooms.builder()
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
//    @Override
//    public void run(ApplicationArguments args) {
//        log.info("Fully working!!!");
//        Set<Beds> bedList = new HashSet<>(repository.findAll());
//        Set<Beds> enumList = convertEnum();
//        for (Beds bed : enumList) {
//            if (!bedList.contains(bed)) {
//                repository.save(bed);
//            }
//        }
//        log.info("Finished initializing enums {}", bedList);
//
//    }
//
//    private Set<Beds> convertEnum() {
//        Set<Beds> bedList = new HashSet<>();
//        for (BedSize bed : BedSize.values()) {
//            bedList.add(Beds.builder()
//                    .id(UUID.randomUUID())
//                    .bedSize(bed.toString())
//                    .peopleCount(bed.getCapacity())
//                    .build());
//        }
//        return bedList;
//    }
