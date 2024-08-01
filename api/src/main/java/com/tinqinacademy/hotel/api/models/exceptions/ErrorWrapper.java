package com.tinqinacademy.hotel.api.models.exceptions;

import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ErrorWrapper implements Errors{
    private List<ErrorResponse> errorResponseList;
    private Date date;
}
