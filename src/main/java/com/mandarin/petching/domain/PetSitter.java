package com.mandarin.petching.domain;


import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class PetSitter extends Member {

    @Id
    @Column(name = "sitter_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String selfIntroduction;

    private String fee;

    private String certificate;

    private String workingArea;

    private String workingTime;

    private String ableService;


}
