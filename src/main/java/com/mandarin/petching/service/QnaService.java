package com.mandarin.petching.service;

import com.mandarin.petching.repository.BoardRepository;
import com.mandarin.petching.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class QnaService {

    private final BoardRepository boardRepository;

    public QnaService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public Page<Board> findBoardList(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize());
        return boardRepository.findAll(pageable);
    }

    public Board findBoardById(Long id) {
        return (Board)this.boardRepository.findById(id).orElse(new Board());

    }

    public Board saveAndUpdateBoard(Board board) {
        return boardRepository.save(board);
    }
}