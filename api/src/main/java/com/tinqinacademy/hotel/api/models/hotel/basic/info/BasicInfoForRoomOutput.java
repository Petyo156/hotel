package com.tinqinacademy.hotel.api.models.hotel.basic.info;

import lombok.*;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class BasicInfoForRoomOutput {
    private String roomId;
    private BigDecimal price;
    private Integer floor;
    private String bedSize;
    private String bathroomType;
    private Integer bedCount;
    private List<LocalDate> datesOccupied;
}
