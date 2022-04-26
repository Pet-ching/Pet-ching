package com.mandarin.petching.controller.qna;

import com.mandarin.petching.domain.*;
import com.mandarin.petching.repository.BoardRepository;
import com.mandarin.petching.repository.MemberRepository;
import com.mandarin.petching.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@RestController
@RequestMapping("qna/boards")
@RequiredArgsConstructor
public class QnaRestController {

    private final BoardRepository boardRepository;
    private final QnaService qnaService;
    private final MemberRepository memberRepository;


    //일괄 삭제
    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> delete(Authentication authentication, @PageableDefault Pageable pageable, Model model) {
        String userName = authentication.getName();
        Member member = memberRepository.findByUserEmail(userName);

        qnaService.deleteQnaAll(member);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    //보드 글 관리
    @Transactional
    @PostMapping//생성
    public ResponseEntity<?> postBoard(Authentication authentication, @RequestBody Board board) {
        String userName = authentication.getName();
        Member member = memberRepository.findByUserEmail(userName);
        board.setMember(member);

        board.setAnswerType(AnswerType.대기);
        board.setRegDate(LocalDateTime.now());
        boardRepository.save(board);
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    @Transactional
    @PutMapping("/{id}")//수정
    public ResponseEntity<?> putBoard(@PathVariable("id")Long id, @RequestBody Board board) {
        //valid 체크
        Board persistBoard = boardRepository.getById(id) ;

//        persistBoard.builder().title(board.getTitle()).content(board.getContent());

       persistBoard.setBoardType(board.getBoardType());
       persistBoard.setTitle(board.getTitle());
       persistBoard.setContent(board.getContent());
       persistBoard.setRegDate(LocalDateTime.now());

        boardRepository.save(persistBoard);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable("id")Long id) {
        //valid 체크
        boardRepository.deleteById(id);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

}