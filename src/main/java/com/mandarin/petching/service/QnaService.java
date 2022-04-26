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

    public boolean isAdmin(Member member)
    {
        if( member.getRole().name().equals(Role.ADMIN.name()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public Page<Board> findQnaAllList(Member member, Pageable pageable) {


        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize());

        if(isAdmin(member) == false)
        {
            return this.boardRepository.findByBoardTypeBetweenAndMemberLike(BoardType.QnA문의1, BoardType.QnA문의3, member, pageable);
        }
        else
        {
            return this.boardRepository.findByBoardTypeBetween(BoardType.QnA문의1, BoardType.QnA문의3, pageable);
        }
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
    public List<Board> deleteQnaAll(Member member)
    {
        List<Board> deletedList  = boardRepository.findByBoardTypeBetweenAndMemberLike(BoardType.QnA문의1 ,BoardType.QnA문의3, member);

        //reply 먼저 삭제
        for(int i=0; i< deletedList.size();i++)
        {
            replyRepository.deleteByBoard(deletedList.get(i));
        }

        if(isAdmin(member) == false)
        {

            deletedList = boardRepository.deleteByBoardTypeBetweenAndMemberLike(BoardType.QnA문의1 ,BoardType.QnA문의3, member);
        }
        else
        {
           deletedList = boardRepository.deleteByBoardTypeBetween(BoardType.QnA문의1 ,BoardType.QnA문의3);
        }

        return deletedList;
    }

//    검색
    public Page<Board> search(Search st, Member member,  Pageable pageable)
    {
        if(SearchType.title.equals(st.getType())) //제목
        {
            if (isAdmin(member) == false)
            {
                return boardRepository.findByBoardTypeBetweenAndTitleContainingAndMemberLike(BoardType.QnA문의1, BoardType.QnA문의3, st.getKeyword(), member, pageable);
            }
            else
            {
                return boardRepository.findByBoardTypeBetweenAndTitleContaining(BoardType.QnA문의1, BoardType.QnA문의3, st.getKeyword(), pageable);
            }
        }
        else if(SearchType.content.equals(st.getType())) // 내용
        {
            if (isAdmin(member) == false) {
                return boardRepository.findByBoardTypeBetweenAndContentContainingAndMemberLike(BoardType.QnA문의1, BoardType.QnA문의3, st.getKeyword(), member, pageable);
            }
            else
            {
                return boardRepository.findByBoardTypeBetweenAndContentContaining(BoardType.QnA문의1, BoardType.QnA문의3, st.getKeyword(), pageable);
            }
        }
        else if(SearchType.titleOrContent.equals(st.getType())) //제목 + 내용
        {
            if (isAdmin(member) == false) {
                return boardRepository.findByBoardTypeBetweenAndTitleContainingOrContentContainingAndMemberLike(BoardType.QnA문의1, BoardType.QnA문의3,st.getKeyword(), st.getKeyword(), member,pageable);
            }
            else
            {
                return boardRepository.findByBoardTypeBetweenAndTitleContainingOrContentContaining(BoardType.QnA문의1, BoardType.QnA문의3,st.getKeyword(), st.getKeyword(),pageable);
            }

        }
        return this.boardRepository.findByBoardTypeBetweenAndMemberLike(BoardType.QnA문의1,BoardType.QnA문의3, member ,pageable);
    }

    //답변
    public Reply reply(Board board)
    {
            return replyRepository.findByBoard(board);
    }

//    public void deleteByBoard(Board board)
//    {
//        replyRepository.deleteByBoard(board);
//    }


}