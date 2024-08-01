package com.tinqinacademy.hotel.core.processors.system;

import com.tinqinacademy.hotel.api.models.exceptions.Errors;
import com.tinqinacademy.hotel.api.models.operations.hotel.unbookbookedroom.UnbookBookedRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.system.adminreportvisitor.AdminReportVisitorInput;
import com.tinqinacademy.hotel.api.models.operations.system.adminreportvisitor.AdminReportVisitorOperation;
import com.tinqinacademy.hotel.api.models.operations.system.adminreportvisitor.AdminReportVisitorOutput;
import com.tinqinacademy.hotel.core.errorhandling.ErrorMapper;
import com.tinqinacademy.hotel.core.processors.BaseOperationProcessor;
import com.tinqinacademy.hotel.persistance.entities.Reservation;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@Slf4j
@Service
public class AdminReportVisitorOperationProcessor extends BaseOperationProcessor implements AdminReportVisitorOperation {

    @Autowired
    public AdminReportVisitorOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper, Validator validator) {
        super(conversionService, errorMapper, validator);
    }

    @Override
    public Either<Errors, AdminReportVisitorOutput> process(AdminReportVisitorInput input) {
        return Try.of(() -> {
                    log.info("Start adminReport input: {}", input);

                    AdminReportVisitorOutput output = AdminReportVisitorOutput.builder()
                            .cardIssueAuthorityID(input.getCardNoID())
                            .startDate(input.getStartDate())
                            .endDate(input.getEndDate())
                            .startDate(input.getStartDate())
                            .firstName(input.getFirstName())
                            .lastName(input.getLastName())
                            .phoneNo(input.getPhoneNo())
                            .cardNoID(input.getCardNoID())
                            .cardValidityID(input.getCardValidityID())
                            .cardIssueAuthorityID(input.getCardIssueAuthorityID())
                            .cardIssueDateID(input.getCardIssueDateID())
                            .visitorsData(new ArrayList<>())
                            .build();

                    log.info("End adminReport output: {}", output);
                    return output;
                })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        Case($(instanceOf(IllegalArgumentException.class)),
                                errorMapper.handleError(throwable, HttpStatus.BAD_REQUEST))
                ));
    }
}
