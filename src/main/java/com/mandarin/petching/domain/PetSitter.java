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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Lob
    private String selfIntroduction;

    private String fee;

    private String certificate;

    private String workingArea;

    private String workingTime;

    private String ableService;
}
