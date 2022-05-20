package com.mandarin.petching.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class ReservationDTO {
    private boolean approval;
    private int fee;


    private LocalDateTime startDate;
    private LocalDateTime endDate;

    //이름
    private Long petOwnerId;
    private Long petSitterId;
    private String petOwnerName;
    private String petSitterName;

    private Long petId;
    private String petName;
}
