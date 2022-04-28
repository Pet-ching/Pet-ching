package com.mandarin.petching.service;

import com.mandarin.petching.domain.Board;
import com.mandarin.petching.domain.ImageType;
import com.mandarin.petching.domain.Images;
import com.mandarin.petching.dto.ImagesDto;
import com.mandarin.petching.repository.BoardRepository;
import com.mandarin.petching.repository.ImagesRepository;
import com.mandarin.petching.util.MD5Generator;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.util.List;

//@RequiredArgsConstructor
@Service
public class ImagesService {

    @Autowired
    private ImagesRepository imagesRepository;

    @Autowired
    private BoardRepository boardRepository;

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

    public ImagesDto storeFile(Authentication authentication, @RequestParam("file") MultipartFile files, @PageableDefault Pageable pageable, Model model) {


        String lootPath = System.getProperty("user.dir");
        ImagesDto imagesDto = new ImagesDto();
        try {
            imagesDto = new ImagesDto();
            String origFilename = files.getOriginalFilename();
            String filename = new MD5Generator(origFilename).toString();
            /* 실행되는 위치의 'files' 폴더에 파일이 저장됩니다. */
            //System.getProperty("user.dir") 프로젝트 경로
            String savePath = lootPath + "\\src\\main\\resources\\static\\files";
            /* 파일이 저장되는 폴더가 없으면 폴더를 생성합니다. */
            //new File(경로, 이름);
            if (!new File(savePath).exists()) {
                try {
                    new File(savePath).mkdir();//디렉토리 생성
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }

            //파일 제목으로 빈 파일 생성 막기
            if (origFilename != null && origFilename != ""  && origFilename.length()>0) {

                //json 파일 읽기
                JSONParser parser = new JSONParser();
                Reader reader = new FileReader(lootPath + "\\src\\main\\resources\\static\\json\\petchingBoard.json");
                JSONObject jsonObject = (JSONObject) parser.parse(reader);

                //json파일에서 id부분 get하기
                long id = (Long) jsonObject.get("id");
                Board board = boardRepository.getById(id);

                //파일 경로 지정
                String filePath = savePath + "\\" + filename;
                files.transferTo(new File(filePath));

                imagesDto.setImgName(origFilename);
                imagesDto.setServerImgName(filename);
                imagesDto.setImgPath(filePath);
                imagesDto.setImgRegDate(LocalDateTime.now());
                imagesDto.setImageType(ImageType.Qna);
                imagesDto.setBoard(board);


            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return imagesDto;
    }
}
