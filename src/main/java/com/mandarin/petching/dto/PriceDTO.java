package com.mandarin.petching.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriceDTO {

    private Long total;
    private Integer min;
    private Integer max;
    private Double avg;

}
