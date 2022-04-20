package com.mandarin.petching.domain;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
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
    private PetType petType; // TODO

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDate petBth; // TODO

    private Integer weight;

    private boolean neutralization;

    private String hospitalName;

    private String hospitalTel;

    private String hospitalAdr;

    private String note;

    private String vaccination; // TODO

    @OneToMany(mappedBy = "pet")
    private List<Reservation> reservationList = new ArrayList<>();

    public static Pet createPetOwner(Member member, PetOwnerDTO petOwnerDto) {
        Pet pet = new Pet();

        pet.member = member;

        pet.petName = petOwnerDto.getPetName();
        pet.petGender = petOwnerDto.getPetGender();
        pet.petType = petOwnerDto.getPetType();
        pet.petBth = petOwnerDto.getPetBth();
        pet.weight = petOwnerDto.getWeight();
        pet.neutralization = petOwnerDto.isNeutralization();
        pet.hospitalName = petOwnerDto.getHospitalName();
        pet.hospitalTel = petOwnerDto.getHospitalTel();
        pet.hospitalAdr = petOwnerDto.getHospitalAdr();
        pet.note = petOwnerDto.getNote();

        return pet;
    }
}
