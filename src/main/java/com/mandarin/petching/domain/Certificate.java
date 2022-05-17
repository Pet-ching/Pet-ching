package com.mandarin.petching.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class Certificate implements Serializable {

    @EmbeddedId//petsitter에 @Embeddeable 추가했음 복합키 사용
    @ManyToOne
    @JoinColumn(name = "sitter_id")
    private PetSitter petSitter;

    private String certificate;
}
