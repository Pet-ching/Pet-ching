package com.mandarin.petching.domain;

import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Optional;

@Entity
@Getter
public class PetOwner {

    @Id
    @Column(name = "owner_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String petName;

    @Enumerated(EnumType.STRING)
    private GenderType petGender;

    @Enumerated(EnumType.STRING)
    private PetType petType; // TODO

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDate petBth; // TODO

    private Integer weight;

    private boolean neutralization;

    private String hospitalName;

    private String hospitalTel;

    private String hospitalAdr;

    private String note;

    private String vaccination;
}
