package com.mandarin.petching.dto;

import com.mandarin.petching.domain.PetType;
import lombok.Data;

@Data
public class CountByPetTypeDTO {
    private PetType petType;
    private Long count;
}
