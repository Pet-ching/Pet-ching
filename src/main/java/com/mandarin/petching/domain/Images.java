package com.mandarin.petching.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Entity
public class Images implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "image_id")
    private Board board;

    @Enumerated(EnumType.STRING)
    private ImageType imageType;

    @CreatedDate
    private LocalDateTime imgRegDate;

    private String imgName;

    private String imgPath;
}
