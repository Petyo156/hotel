package com.tinqinacademy.hotel.api.models.hotel.basic.info;

import lombok.*;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class BasicInfoForRoomOutput {
    private UUID roomId;
    private BigDecimal price;
    private Integer floor;
    private List<String> bedSize;
    private String bathroomType;
    private List<LocalDate> datesOccupied;
}
