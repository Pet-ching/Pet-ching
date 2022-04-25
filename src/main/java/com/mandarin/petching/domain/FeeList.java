package com.mandarin.petching.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
public class FeeList {

    @Id
    @Column(name = "fee_list")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer smallDogFee;

    private Integer middleDogFee;

    private Integer largeDogFee;

    private Integer catFee;

    private Integer etcFee;
}
