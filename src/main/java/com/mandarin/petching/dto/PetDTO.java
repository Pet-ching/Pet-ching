package com.mandarin.petching.dto;

import com.mandarin.petching.domain.PetType;
import lombok.Data;

@Data
public class PetDTO {


    private String user_name;
    private String user_email;
    private String user_bth;

    private String pet_name;
    private PetType pet_type;
    private int pet_age;
    private boolean neutralization;
}
