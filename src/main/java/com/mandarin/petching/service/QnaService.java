package com.mandarin.petching.service;

import com.mandarin.petching.domain.BoardType;
import com.mandarin.petching.domain.Search;
import com.mandarin.petching.domain.SearchType;
import com.mandarin.petching.repository.BoardRepository;
import com.mandarin.petching.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class QnaService {

    private final BoardRepository boardRepository;

    public QnaService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Transactional
    public Page<Board> findQnaAllList(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize());
        return this.boardRepository.findByBoardTypeOrBoardTypeOrBoardType(BoardType.QnA문의1, BoardType.QnA문의2, BoardType.QnA문의3, pageable);
//        return boardRepository.findAll(pageable);
    }

    @Transactional
    public Board findBoardById(Long id) {
        return (Board)this.boardRepository.findById(id).orElse(new Board());
    }

    @Transactional
    public Board saveAndUpdateBoard(Board board) {
        return boardRepository.save(board);
    }

    @Transactional
    public List<Board> deleteQnaAll(BoardType b1, BoardType b2, BoardType b3)
    {
        List<Board> deletedList = boardRepository.deleteByBoardTypeOrBoardTypeOrBoardType(b1,b2,b3);
        return deletedList;
    }

    public Page<Board> search(Search st, Pageable pageable)
    {
        if(SearchType.title.equals(st.getType())) {
            return boardRepository.findByBoardTypeBetweenAndTitleContaining(BoardType.QnA문의1, BoardType.QnA문의3,st.getKeyword(), pageable);
        }
        else if(SearchType.content.equals(st.getType()))
        {
            return boardRepository.findByBoardTypeBetweenAndContentContaining(BoardType.QnA문의1, BoardType.QnA문의3,st.getKeyword(), pageable);
        }
        else if(SearchType.titleOrContent.equals(st.getType()))
        {
            return boardRepository.findByBoardTypeBetweenAndTitleContainingOrContentContaining(BoardType.QnA문의1, BoardType.QnA문의3,st.getKeyword(), st.getKeyword(), pageable);
        }
//        SearchType.type.values()
        return this.boardRepository.findByBoardTypeOrBoardTypeOrBoardType(BoardType.QnA문의1, BoardType.QnA문의2, BoardType.QnA문의3, pageable); //실패 시 전부 검색
    }



//    @Transactional
//    public Page<Board> deleteAll(BoardType boardType1, BoardType boardType2, BoardType boardType3, Pageable pageable)
//    {
//        return this.boardRepository.deleteByBoardTypeOrBoardTypeOrBoardType(boardType1, boardType2, boardType3,pageable);
//    }
//    @Transactional
//    public Page<Board> deleteAll(String boardType1, String boardType2, String boardType3, Pageable pageable)
//    {
//        return this.boardRepository.deleteByBoardTypeOrBoardTypeOrBoardType(boardType1, boardType2, boardType3,pageable);
//    }
////    @Transactional
//    public Page<Board> deleteQnaAll(Pageable pageable)
//    {
//        return this.boardRepository.deleteByBoardTypeOrBoardTypeOrBoardType(BoardType.QnA문의1, BoardType.QnA문의2, BoardType.QnA문의3, pageable);
//    }

//    public Page<Board>


    //전부삭제

}