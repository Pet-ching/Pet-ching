package com.mandarin.petching.dto;

import com.mandarin.petching.domain.GenderType;
import com.mandarin.petching.domain.PetType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Getter @Setter
@ToString
public class PetDto {

    private String petName;

    private GenderType petGender;

    private PetType petType;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDate petBth;

    private Integer weight;

    private boolean neutralization;

    private String hospitalName;

    private String hospitalTel;

    private String hospitalAdr;

    private String note;
}
