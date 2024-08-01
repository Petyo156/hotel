package com.tinqinacademy.hotel.api.models.operations.system.adminpartialupdate;

import com.tinqinacademy.hotel.api.models.base.OperationOutput;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class AdminPartialUpdateOutput implements OperationOutput {
    private String id;
}
