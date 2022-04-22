package com.mandarin.petching.service;

import com.mandarin.petching.domain.BoardType;
import com.mandarin.petching.repository.BoardRepository;
import com.mandarin.petching.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
public class QnaService {

    private final BoardRepository boardRepository;

    public QnaService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public Page<Board> findQnaAllList(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize());
        return this.boardRepository.findByBoardTypeOrBoardTypeOrBoardType(BoardType.QnA문의1, BoardType.QnA문의2, BoardType.QnA문의3, pageable);
//        return boardRepository.findAll(pageable);
    }

    public Board findBoardById(Long id) {
        return (Board)this.boardRepository.findById(id).orElse(new Board());
    }
    public Board saveAndUpdateBoard(Board board) {
        return boardRepository.save(board);
    }

//    @Transactional
//    public Page<Board> deleteQnaAll(Pageable pageable)
//    {
//        return this.boardRepository.deleteByBoardTypeOrBoardTypeOrBoardType(BoardType.QnA문의1, BoardType.QnA문의2, BoardType.QnA문의3, pageable);
//    }

//    public Page<Board>


    //전부삭제

}