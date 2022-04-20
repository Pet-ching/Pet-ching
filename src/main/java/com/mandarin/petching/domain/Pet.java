package com.mandarin.petching.domain;

import com.mandarin.petching.dto.PetDto;
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

    public static Pet createPet(Member member, PetDto petDto) {
        Pet pet = new Pet();

        pet.member = member;

        pet.petName = petDto.getPetName();
        pet.petGender = petDto.getPetGender();
        pet.petType = petDto.getPetType();
        pet.petBth = petDto.getPetBth();
        pet.weight = petDto.getWeight();
        pet.neutralization = petDto.isNeutralization();
        pet.hospitalName = petDto.getHospitalName();
        pet.hospitalTel = petDto.getHospitalTel();
        pet.hospitalAdr = petDto.getHospitalAdr();
        pet.note = petDto.getNote();

        return pet;
    }

    public void updatePet(PetDto petDto) {
        this.petName = petDto.getPetName();
        this.petGender = petDto.getPetGender();
        this.petType = petDto.getPetType();
        this.petBth = petDto.getPetBth();
        this.weight = petDto.getWeight();
        this.neutralization = petDto.isNeutralization();
        this.hospitalName = petDto.getHospitalName();
        this.hospitalTel = petDto.getHospitalTel();
        this.hospitalAdr = petDto.getHospitalAdr();
        this.note = petDto.getNote();
    }
}
