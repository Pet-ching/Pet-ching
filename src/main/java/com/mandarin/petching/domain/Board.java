package com.mandarin.petching.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
public class Board {

    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member member;

    private Integer hits;

    @CreatedDate
    private LocalDateTime regDate;

    @Column(length = 20)
    private String title;

    @Lob
    private String content;

    @Enumerated(EnumType.STRING)
    private BoardType boardType;

//    @OneToMany(mappedBy = "board",fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
//    @JsonIgnoreProperties({"board"})
//    @OrderBy("id desc")
//    private List<Reply> replys = new ArrayList<>();
}
