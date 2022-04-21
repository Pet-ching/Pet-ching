package com.mandarin.petching.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@ToString
public class PetSitter {

    @Id
    @Column(name = "sitter_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id", unique = true)
    private Member member;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fee_list", unique = true)
    private FeeList feeList;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "working_id")
    private WorkingDayAndTime workingDayAndTime;

    @ElementCollection
    @CollectionTable(name = "certificate",
            joinColumns = @JoinColumn(name = "sitter_id"))
    private List<String> certificate;

    @ElementCollection
    @CollectionTable(name = "certificate",
            joinColumns = @JoinColumn(name = "sitter_id"))
    private List<String> ableService;

    private String workingArea;

    @Lob
    private String selfIntroduction;
}
