package com.mandarin.petching.service;

import com.mandarin.petching.domain.*;
import com.mandarin.petching.repository.BoardRepository;
import com.mandarin.petching.repository.ImagesRepository;
import com.mandarin.petching.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QnaService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;
    private final ImagesRepository imagesRepository;

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

            //멤버가 있어야지 들어갈 수 있는 조건문 때문에 필요
            board.setMember(member);

            return board;
        }
    }

    @Transactional
    public Board saveAndUpdateBoard(Board board) {
        return boardRepository.save(board);
    }

// 게시글 한개 삭제
    @Transactional
    public void deleteOneBoard(Board board)
    {
        //reply, 이미지 먼저 삭제
        replyRepository.deleteByBoard(board);
        imagesRepository.deleteByBoard(board);

        boardRepository.deleteById(board.getId());

    }


//    전부삭제
    @Transactional
//    public List<Board> deleteQnaAll(Member member)
    public void deleteQnaAll(Member member)
    {
        List<Board> deletedList;

        //일반 유저
        if(isAdmin(member) == false)
        {
            deletedList  = boardRepository.findByBoardTypeBetweenAndMemberLike(BoardType.QnA문의1 ,BoardType.QnA문의3, member);
            //FK 엮인 것들 먼저 삭제
            this.deleteBoardFkDomain(deletedList);

            boardRepository.deleteByBoardTypeBetweenAndMemberLike(BoardType.QnA문의1 ,BoardType.QnA문의3, member);
        }
        //관리자
        else
        {
            deletedList  = boardRepository.findByBoardTypeBetween(BoardType.QnA문의1 ,BoardType.QnA문의3);
            //FK 엮인 것들 먼저 삭제
            this.deleteBoardFkDomain(deletedList);

            boardRepository.deleteByBoardTypeBetween(BoardType.QnA문의1 ,BoardType.QnA문의3);

        }

    }

    public void deleteBoardFkDomain(List<Board> deletedList)
    {
        for(int i=0; i< deletedList.size();i++)
        {
            if(replyRepository.findByBoard(deletedList.get(i)) != null) {
                replyRepository.deleteByBoard(deletedList.get(i));
            }
            if(imagesRepository.findByBoard(deletedList.get(i)) != null) {
                imagesRepository.deleteByBoard(deletedList.get(i));
            }
        }
    }

//    검색
    public Page<Board> search(Search st, Member member, Pageable pageable)
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

    public void saveBoard(Board board)
    {
        boardRepository.save(board);
    }
}