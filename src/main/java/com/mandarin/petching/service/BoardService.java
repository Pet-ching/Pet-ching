package com.mandarin.petching.service;

import com.mandarin.petching.domain.Board;
import com.mandarin.petching.domain.BoardType;
import com.mandarin.petching.domain.Member;
import com.mandarin.petching.domain.Reply;
import com.mandarin.petching.repository.BoardRepository;
import com.mandarin.petching.repository.MemberRepository;
import com.mandarin.petching.repository.ReplyRepository;
import com.mandarin.petching.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@Service
@Transactional
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReplyRepository replyRepository;

    public Board save(String userName, Board board) {
       Member member =  memberRepository.findByUserEmail(userName);
       board.setMember(member);
       board.setHits(0);
       board.setBoardType(BoardType.COMMUNITY);
       return boardRepository.save(board);
    }

    public int updateHits(Long id) {
        return boardRepository.updateHits(id);
    }

    public void saveReply(Reply reply, Board board, String userEmail) {

        Member member = memberRepository.findByUserEmail(userEmail);

        reply.setBoard(board);
        reply.setMember(member);

        replyRepository.save(reply);
    }

    public void deleteReply(Long replyId) {
        replyRepository.deleteById(replyId);
    }
}
