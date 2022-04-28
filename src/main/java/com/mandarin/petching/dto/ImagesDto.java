package com.mandarin.petching.dto;


import com.mandarin.petching.domain.Board;
import com.mandarin.petching.domain.ImageType;
import com.mandarin.petching.domain.Images;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class ImagesDto {

    private Long id;

    private Board board; //보드

    private ImageType imageType; //확장자

    private LocalDateTime imgRegDate; //날짜

    private String imgName; //이름

    private String serverImgName; //서버용 이미지 이름

    private String imgPath; //경로


    public Images toEntity()
    {
        Images images = Images.builder()
                .id(id)
                .board(board)
                .imageType(imageType)
                .imgName(imgName)
                .imgPath(imgPath)
                .imgRegDate(imgRegDate)
                .serverImgName(serverImgName)
                .build();
        return  images;
    }

    @Builder
    public ImagesDto(Long id, Board board, ImageType imageType, LocalDateTime imgRegDate, String imgName, String serverImgName, String imgPath) {
        this.id = id;
        this.board = board;
        this.imageType = imageType;
        this.imgRegDate = imgRegDate;
        this.imgName = imgName;
        this.serverImgName = serverImgName;
        this.imgPath = imgPath;
    }
}
