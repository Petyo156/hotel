package com.tinqinacademy.hotel.core.converters;

import com.tinqinacademy.hotel.api.models.operations.system.registervisitor.RegisterVisitorsDataInput;
import com.tinqinacademy.hotel.persistance.entities.Guest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RegisterVisitorsDataOutputConverter implements Converter<RegisterVisitorsDataInput, Guest.GuestBuilder> {
    @Override
    public Guest.GuestBuilder convert(RegisterVisitorsDataInput dataInput) {
        return Guest.builder()
                .IdCardNumber(dataInput.getCardNoID())
                .IdCardIssueDate(dataInput.getCardIssueDateID())
                .firstName(dataInput.getFirstName())
                .lastName(dataInput.getLastName())
                .phoneNo(dataInput.getPhoneNo())
                .IdCardValidity(dataInput.getCardValidityID())
                .IdCardAuthority(dataInput.getCardIssueAuthorityID())
                .birthdate(dataInput.getBirthDate());
    }
}
