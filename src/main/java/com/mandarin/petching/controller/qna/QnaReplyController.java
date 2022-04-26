package com.mandarin.petching.controller.qna;

import com.mandarin.petching.domain.AnswerType;
import com.mandarin.petching.domain.Board;
import com.mandarin.petching.domain.Member;
import com.mandarin.petching.domain.Reply;
import com.mandarin.petching.repository.BoardRepository;
import com.mandarin.petching.repository.MemberRepository;
import com.mandarin.petching.repository.ReplyRepository;
import com.mandarin.petching.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@RestController
@RequestMapping("qna/replies")
@RequiredArgsConstructor
public class QnaReplyController {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private  final ReplyRepository replyRepository;
    private final QnaService qnaService;

    //답변 글 관리
    @Transactional
    @PostMapping("/{id}")//생성
    public ResponseEntity<?> postReply(Authentication authentication, @PathVariable("id")Long id, @RequestBody Reply reply) {
        String userName = authentication.getName();
        Member member = memberRepository.findByUserEmail(userName);

        Board board = boardRepository.getById(id) ;
        board.setAnswerType(AnswerType.완료);
        boardRepository.save(board);


        reply.setMember(member);
        reply.setBoard(board);
        reply.setReRegDate(LocalDateTime.now());
        replyRepository.save(reply);

        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }


    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReply(@PathVariable("id")Long id) {
        Board board = boardRepository.getById(id);

        //qna 와 답변 모두 삭제
        replyRepository.deleteByBoard(board);
        boardRepository.deleteById(id);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
