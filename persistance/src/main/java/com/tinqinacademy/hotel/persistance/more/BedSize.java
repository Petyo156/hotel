package com.tinqinacademy.hotel.persistance.more;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum BedSize {
    SINGLE("single"),
    DOUBLE("double"),
    SMALLDOUBLE("smallDouble"),
    KINGSIZE("kingSize"),
    QUEENSIZE("queenSize"),
    UNKNOWN("");

    private final String code;

    BedSize(String code) {
        this.code = code;
    }

    @JsonCreator
    public static BedSize getByCode(String input) {
        for (BedSize bed : BedSize.values()) {
            if (bed.toString().equals(input)) {
                return bed;
            }
        }
        return BedSize.UNKNOWN;
    }

    @Override
    @JsonValue
    public String toString() {
        return this.code;
    }
}

//@Getter
//public enum BedSize {
//    SINGLE("single", 1),
//    DOUBLE("double", 2),
//    SMALLDOUBLE("smallDouble", 2),
//    KINGSIZE("kingSize", 3),
//    QUEENSIZE("queenSize", 3),
//    UNKNOWN("", 0);
//
//    private final String code;
//    private final Integer capacity;
//
//    BedSize(String code, Integer capacity) {
//        this.code = code;
//        this.capacity = capacity;
//    }
//
//    @JsonCreator
//    public static BedSize getByCode(String input) {
//        for (BedSize bed : BedSize.values()) {
//            if (bed.toString().equals(input)) {
//                return bed;
//            }
//        }
//        return BedSize.UNKNOWN;
//    }
//
//    @Override
//    @JsonValue
//    public String toString() {
//        return this.code;
//    }
//
//    public Integer getCapacity() {
//        return capacity;
//    }
//}
