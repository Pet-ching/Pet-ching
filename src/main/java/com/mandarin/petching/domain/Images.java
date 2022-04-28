package com.mandarin.petching.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
//public class Images implements Serializable {
public class Images{

    @Id
    @Column(name="image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board; //보드

    @Enumerated(EnumType.STRING)
    private ImageType imageType;

    @CreatedDate
    private LocalDateTime imgRegDate; //날짜

    private String imgName; //이름

    private String serverImgName; //서버용 이미지 이름

    private String imgPath; //경로

    @Builder
    public Images(Long id, Board board, ImageType imageType, LocalDateTime imgRegDate, String imgName, String serverImgName, String imgPath) {
        this.id = id;
        this.board = board;
        this.imageType = imageType;
        this.imgRegDate = imgRegDate;
        this.imgName = imgName;
        this.serverImgName = serverImgName;
        this.imgPath = imgPath;
    }
}
