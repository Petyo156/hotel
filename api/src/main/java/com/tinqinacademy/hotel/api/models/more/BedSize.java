package com.tinqinacademy.hotel.api.models.more;

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
