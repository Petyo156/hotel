package com.tinqinacademy.hotel.persistance.initialize;

import com.tinqinacademy.hotel.persistance.entities.Beds;
import com.tinqinacademy.hotel.persistance.more.BedSize;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Component
@Log4j2
public class CustomApplicationRunner implements ApplicationRunner {
    //We’ll want to use an ApplicationRunner if we need to create
    // some global startup logic with access to complex command-line arguments.
    @Override
    public void run(ApplicationArguments args){
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
