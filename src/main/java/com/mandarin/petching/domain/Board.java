package com.mandarin.petching.domain;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class , property = "id")
public class Board {

    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    private Member member;

    @Column(columnDefinition = "integer default 0")
    private Integer hits;

    @Column(name = "reg_date", updatable = false)
    @CreationTimestamp
    private LocalDateTime regDate;


    @Column(length = 20 )
    @NotBlank(message = "제목을 입력해 주세요!")
    private String title;

    @Lob
    @NotBlank(message = "제목을 입력해 주세요!")
    private String content;

    @NotNull(message = "보드 타입을 선택해 주세요!")
    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    @Enumerated(EnumType.STRING)// Enum 타입의 필드를 DB에 저장할 때 enum '이름'으로 매핑해주는 어노테이션
    private AnswerType answerType;

//    @JsonManagedReference
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Reply> replies;

    @Builder
    public Board(String title, String content, BoardType boardType, LocalDateTime regDate, Member member, AnswerType answerType) {
        this.title = title;
        this.content = content;
        this.boardType = boardType;
        this.regDate = regDate;
        this.member = member;
        this.answerType = answerType;
//        this.ImageId = ImageId;
    }
}

