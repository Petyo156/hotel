package com.tinqinacademy.hotel.persistance.more;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum BathroomType {
    PRIVATE("private"),
    SHARED("shared"),
    UNKNOWN("");

    private final String code;

    BathroomType(String code) {
        this.code = code;
    }

    @JsonCreator
    public static BathroomType getByCode(String input) {
        for (BathroomType bathroom : BathroomType.values()) {
            if (bathroom.toString().equals(input)) {
                return bathroom;
            }
        }
        return BathroomType.UNKNOWN;
    }

    @JsonValue
    @Override
    public String toString() {
        return this.code;
    }
}
