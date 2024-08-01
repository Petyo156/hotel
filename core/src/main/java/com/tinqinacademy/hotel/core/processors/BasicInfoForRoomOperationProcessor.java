package com.tinqinacademy.hotel.core.processors;

import com.tinqinacademy.hotel.api.models.exceptions.Errors;
import com.tinqinacademy.hotel.api.models.operations.hotel.basicinfo.BasicInfoForRoomInput;
import com.tinqinacademy.hotel.api.models.operations.hotel.basicinfo.BasicInfoForRoomOperation;
import com.tinqinacademy.hotel.api.models.operations.hotel.basicinfo.BasicInfoForRoomOutput;
import io.vavr.control.Either;
import io.vavr.control.Try;

import static io.vavr.API.Match;

public class BasicInfoForRoomOperationProcessor implements BasicInfoForRoomOperation {
    @Override
    public Either<Errors, BasicInfoForRoomOutput> process(BasicInfoForRoomInput input) {
//        return Try.of(() -> {
//
//        })
//                .toEither()
//                .mapLeft(throwable -> Match(throwable).of(
//
//        ));
        return null;
    }
}
