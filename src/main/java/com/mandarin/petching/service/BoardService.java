package com.mandarin.petching.service;

import com.mandarin.petching.domain.Board;
import com.mandarin.petching.domain.Member;
import com.mandarin.petching.repository.BoardRepository;
import com.mandarin.petching.repository.MemberRepository;
import com.mandarin.petching.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MemberRepository memberRepository;

    public Board save(String userName, Board board) {
       Member member =  memberRepository.findByUserEmail(userName);
       board.setMember(member);
       board.setHits(0);
       return boardRepository.save(board);
    }

    @Transactional
    public int updateHits(Long id) {
        return boardRepository.updateHits(id);
    }

}
