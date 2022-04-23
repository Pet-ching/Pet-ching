package com.mandarin.petching.domain;

import com.mandarin.petching.dto.PetDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Pet {

    @Id
    @Column(name = "pet_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String petName;

    @Enumerated(EnumType.STRING)
    private GenderType petGender;

    @Enumerated(EnumType.STRING)
    private PetType petType;

    private Integer petAge;

    private Integer weight;

    private boolean neutralization;

    private String hospitalName;

    private String hospitalTel;

    private String hospitalAdr;

    private String note;

    @OneToMany(mappedBy = "pet")
    private List<Reservation> reservationList = new ArrayList<>();
}
