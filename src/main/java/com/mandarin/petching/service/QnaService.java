package com.mandarin.petching.service;

import com.mandarin.petching.domain.*;
import com.mandarin.petching.repository.BoardRepository;
import com.mandarin.petching.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QnaService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;



    public Page<Board> findQnaAllList(Member member, Pageable pageable) {


        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize());
        return this.boardRepository.findByBoardTypeBetweenAndMemberLike(BoardType.QnA문의1,BoardType.QnA문의3, member,pageable);

    }


    public Board findBoardById(Long id, Member member) {

        if(!this.boardRepository.findById(id).isEmpty())
        {
            return this.boardRepository.getById(id);
        }
        else
        {
            Board board = new Board();
            board.setMember(member);
            return board;
        }
    }

    @Transactional
    public Board saveAndUpdateBoard(Board board) {
        return boardRepository.save(board);
    }


//    전부삭제
    @Transactional
    public List<Board> deleteQnaAll(BoardType start, BoardType last, Member member)
    {
        List<Board> deletedList = boardRepository.deleteByBoardTypeBetweenAndMemberLike(start, last, member);
        return deletedList;
    }


//    검색
    public Page<Board> search(Search st, Member member,  Pageable pageable)
    {
        if(SearchType.title.equals(st.getType())) {
            return boardRepository.findByBoardTypeBetweenAndTitleContainingAndMemberLike(BoardType.QnA문의1, BoardType.QnA문의3,st.getKeyword(), member,pageable);
        }
        else if(SearchType.content.equals(st.getType()))
        {
            return boardRepository.findByBoardTypeBetweenAndContentContainingAndMemberLike(BoardType.QnA문의1, BoardType.QnA문의3,st.getKeyword(), member, pageable);
        }
        else if(SearchType.titleOrContent.equals(st.getType()))
        {
            return boardRepository.findByBoardTypeBetweenAndTitleContainingOrContentContainingAndMemberLike(BoardType.QnA문의1, BoardType.QnA문의3,st.getKeyword(), st.getKeyword(), member,pageable);
        }
        return this.boardRepository.findByBoardTypeBetweenAndMemberLike(BoardType.QnA문의1,BoardType.QnA문의3, member ,pageable);
    }

    //답변
    public Reply reply(Board board)
    {
        return replyRepository.findByBoard(board);
    }




}