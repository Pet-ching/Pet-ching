package com.mandarin.petching.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class , property = "id")
public class Reply {

    @Id
    @Column(name="reply_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="board_id", referencedColumnName = "board_id")
    private Board board;


    @NotEmpty(message = "255자 내로 내용을 입력해주세요")
    private String reContent;

    @CreatedDate
    private LocalDateTime reRegDate;

//    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="user_id")
    private Member member;
}
