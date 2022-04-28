package com.mandarin.petching.controller.qna;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mandarin.petching.dto.ImagesDto;
import com.mandarin.petching.domain.AnswerType;
import com.mandarin.petching.domain.Board;
import com.mandarin.petching.domain.Member;
import com.mandarin.petching.repository.BoardRepository;
import com.mandarin.petching.repository.MemberRepository;
import com.mandarin.petching.service.ImagesService;
import com.mandarin.petching.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

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


//        Map<String, Object> toJson = new BoardDto().toJson(board);

        return new ResponseEntity<>("{}", HttpStatus.CREATED);
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

    //파일 다운로드
    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> fileDownload(@PathVariable("fileId") Long fileId) throws IOException {
        ImagesDto imagesDto = imagesService.getFile(fileId);
        Path path = Paths.get(imagesDto.getImgPath());
        Resource resource = new InputStreamResource(Files.newInputStream(path));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + URLEncoder.encode(imagesDto.getImgName(), "UTF-8")+"\";")
                .body(resource);
    }

}