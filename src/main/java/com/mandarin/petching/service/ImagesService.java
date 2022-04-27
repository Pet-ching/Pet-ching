package com.mandarin.petching.service;

import com.mandarin.petching.domain.Board;
import com.mandarin.petching.domain.Images;
import com.mandarin.petching.dto.ImagesDto;
import com.mandarin.petching.repository.ImagesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

//@RequiredArgsConstructor
@Service
public class ImagesService {

    @Autowired
    private ImagesRepository imagesRepository;

    //저장
    @Transactional
    public void saveImage(ImagesDto imagesDto)
    {
        imagesRepository.save(imagesDto.toEntity());
    }

    //찾기
    public Images findOneByBoard(Board board)
    {
        //object references an unsaved transient instance - save the transient instance before flushing
        //위 문제가 해결되지 않아서 이렇게 짬.
        //board 없을 시
        if (board.getId() != null) {
            List<Images> images = imagesRepository.findByBoard(board);

            if (images.size() > 0) {
                return images.get(0);
            } else {
                return new Images();
            }
        } else {
            return new Images();
        }

    }

    public  ImagesDto getImage(Board board)
    {
        List<Images> images = imagesRepository.findByBoard(board);
        Images image = images.get(0);

        ImagesDto imagesDto = ImagesDto.builder()
                .id(image.getId())
                .board(board)
                .imageType(image.getImageType())
                .imgName(image.getImgName())
                .imgPath(image.getImgPath())
                .serverImgName(image.getServerImgName())
                .imgRegDate(image.getImgRegDate())
                .build();

        return imagesDto;
    }

    //삭제
    public void deleteByBoard(Board board)
    {
        imagesRepository.deleteByBoard(board);
    }

    @Transactional
    public ImagesDto getFile(Long id) {
        Images image = imagesRepository.findById(id).get();

        ImagesDto imagesDto = ImagesDto.builder()
                .id(id)
                .imgName(image.getImgName())
                .serverImgName(image.getServerImgName())
                .imgPath(image.getImgPath())
                .build();
        return imagesDto;
    }
}
