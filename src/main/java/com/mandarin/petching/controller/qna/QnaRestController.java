package com.mandarin.petching.controller.qna;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mandarin.petching.domain.*;
import com.mandarin.petching.dto.BoardDto;
import com.mandarin.petching.dto.ImagesDto;
import com.mandarin.petching.repository.BoardRepository;
import com.mandarin.petching.repository.ImagesRepository;
import com.mandarin.petching.repository.MemberRepository;
import com.mandarin.petching.service.ImagesService;
import com.mandarin.petching.service.QnaService;
import com.mandarin.petching.util.MD5Generator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("qna/boards")
@RequiredArgsConstructor
public class QnaRestController {

    private final BoardRepository boardRepository;
    private final QnaService qnaService;
    private final MemberRepository memberRepository;
    private final ImagesService imagesService;


    //일괄 삭제
    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> delete(Authentication authentication, @PageableDefault Pageable pageable, Model model) {
        String userName = authentication.getName();
        Member member = memberRepository.findByUserEmail(userName);

        qnaService.deleteQnaAll(member);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    //보드 글 관리

    //생성
//    @JsonIgnoreProperties
    @Transactional
    @PostMapping
    public ResponseEntity<?> postBoard(Authentication authentication, @RequestBody Board board) throws IOException {
        String userName = authentication.getName();
        Member member = memberRepository.findByUserEmail(userName);
        board.setMember(member);

        board.setAnswerType(AnswerType.대기);
        board.setRegDate(LocalDateTime.now());
        boardRepository.save(board);


        board = boardRepository.getById(board.getId());


        //Java object to json
        String lootPath = System.getProperty("user.dir");
        ObjectMapper mapper = new ObjectMapper();

        //직렬화 오류 해결 위해 사용
        mapper.registerModule(new JavaTimeModule());
        File file = new File(lootPath + "\\src\\main\\resources\\static\\json\\petchingBoard.json");
        FileOutputStream fileOutputStream = new FileOutputStream(file); //ture 이어쓰기 false 덮어쓰기
        mapper.writeValue(fileOutputStream, board);


        Map<String, Object> toJson = new BoardDto().toJson(board);

        return new ResponseEntity<>(toJson, HttpStatus.CREATED);
    }






    @Transactional
    @PutMapping("/{id}")//수정
///매겨변수 이름이랑 html name 이름이랑 일치시켜야 한다
    public ResponseEntity<?> putBoard(@PathVariable("id")Long id, @RequestBody Board board) {
        //valid 체크
        Board persistBoard = boardRepository.getById(id) ;

//        persistBoard.builder().title(board.getTitle()).content(board.getContent());
       persistBoard.setBoardType(board.getBoardType());
       persistBoard.setTitle(board.getTitle());
       persistBoard.setContent(board.getContent());
       persistBoard.setRegDate(LocalDateTime.now());

//        qnaService.write(board,file);

        boardRepository.save(persistBoard);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable("id")Long id) {

        Board board = boardRepository.getById(id);
        qnaService.deleteOneBoard(board);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

//    @PostMapping("/image")
//    public ResponseEntity<?> write(@RequestParam("file") MultipartFile files) {
//        try {
//            String origFilename = files.getOriginalFilename();
//            String filename = new MD5Generator(origFilename).toString();
//            /* 실행되는 위치의 'files' 폴더에 파일이 저장됩니다. */
//            //System.getProperty("user.dir") 프로젝트 경로
//            String savePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";
//            /* 파일이 저장되는 폴더가 없으면 폴더를 생성합니다. */
//            //new File(경로, 이름);
//            if (!new File(savePath).exists()) {
//                try{
//                    new File(savePath).mkdir();//디렉토리 생성
//                }
//                catch(Exception e){
//                    e.getStackTrace();
//                }
//            }
//            String filePath = savePath + "\\" + filename;
//            files.transferTo(new File(filePath));
//
//            ImagesDto imagesDto = new ImagesDto();
//            imagesDto.setImgName(origFilename);
//            imagesDto.setServerImgName(filename);
//            imagesDto.setImgPath(filePath);
//
//            Long ImageId = imagesService.saveImage(imagesDto);
//
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//       return new ResponseEntity<>("{}", HttpStatus.OK);
//    }

}