package com.mandarin.petching.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@Entity
@Getter @Setter
@ToString
@NoArgsConstructor
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

    @ElementCollection
    @CollectionTable(name = "workingDay",
            joinColumns = @JoinColumn(name = "sitter_id"))
    private List<String> workingDay;

    @ElementCollection
    @CollectionTable(name = "certificate",
            joinColumns = @JoinColumn(name = "sitter_id"))
    private List<String> certificate;

    @ElementCollection
    @CollectionTable(name = "ableService",
            joinColumns = @JoinColumn(name = "sitter_id"))
    private List<String> ableService;

    @NotBlank
    private String workingArea;

    @Lob
    @NotBlank
    private String selfIntroduction;

    @NotBlank
    private String title;

    @ElementCollection
    @CollectionTable(name = "images",
            joinColumns = @JoinColumn(name = "sitter_id"))
    private List<String> imgPaths = new ArrayList<>();

    //    @JsonManagedReference
    @OneToMany(mappedBy = "petsitter", cascade = CascadeType.REMOVE)
    private List<Review> replies;
}
