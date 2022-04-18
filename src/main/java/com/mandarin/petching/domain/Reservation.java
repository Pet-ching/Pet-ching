package com.mandarin.petching.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Reservation {

    @Id
    @Column(name = "reservation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pet_owner")
    private PetOwner petOwner;

    @ManyToOne
    @JoinColumn(name = "pet_sitter")
    private PetSitter petSitter;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private boolean approval;

    private Integer fee;
}
