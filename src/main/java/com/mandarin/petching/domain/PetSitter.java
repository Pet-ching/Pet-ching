package com.mandarin.petching.domain;


import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;

@Entity
@Getter @Setter
public class PetSitter {

    @Id
    @Column(name = "sitter_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id", unique = true)
    private Member member;

    @Lob
    private String selfIntroduction;

    private String fee;

    private String certificate;

    private String workingArea;

    private String workingTime;

    private String ableService;

    public static PetSitter createPetSitter(Member member, PetSitter petSitterTemp) {
        PetSitter petSitter = new PetSitter();

        petSitter.setMember(member);

        petSitter.setWorkingTime(petSitterTemp.workingTime);
        petSitter.setWorkingArea(petSitterTemp.getWorkingArea());
        petSitter.setFee(petSitterTemp.getFee());
        petSitter.setCertificate(petSitterTemp.getCertificate());
        petSitter.setSelfIntroduction(petSitterTemp.getSelfIntroduction());
        petSitter.setAbleService(petSitterTemp.getAbleService());

        return petSitter;
    }
}
