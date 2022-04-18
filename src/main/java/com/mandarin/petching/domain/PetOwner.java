package com.mandarin.petching.domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class PetOwner extends Member {

    @Id
    @Column(name = "owner_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String petName;

    @Enumerated(EnumType.STRING)
    private GenderType petGender;

    private LocalDate petBth;

    private Integer weight;

    private boolean neutralization;

    private String hospitalName;

    private String hospitalTel;

    private String hospitalAdr;

    private String note;

    private String vaccination;
}
