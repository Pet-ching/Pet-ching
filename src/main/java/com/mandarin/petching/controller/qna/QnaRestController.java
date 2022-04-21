package com.mandarin.petching.controller.qna;

import com.mandarin.petching.domain.AnswerType;
import com.mandarin.petching.domain.Board;
import com.mandarin.petching.repository.BoardRepository;
import com.mandarin.petching.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

//import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
//import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


@RestController
@RequestMapping("qna/boards")
@RequiredArgsConstructor
public class QnaRestController {


    private final PagedResourcesAssembler<Board> assembler;

    private final BoardRepository boardRepository;

   private final QnaService qnaService;

// 버전 1 uri 제공 안함
//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)//반환값 json
//    public ResponseEntity<PagedModel<Board>> getBoards(@PageableDefault Pageable pageable) {
//       // Page<Board> boards = this.qnaService.findBoardList(pageable);
//        Page<Board> boards = boardRepository.findAll(pageable);
//        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(pageable.getPageSize(), boards.getNumber(), boards.getTotalElements());
//
//        PagedModel<Board> resources = PagedModel.of(boards.getContent(), pageMetadata);
//        // PagedModel<Board> resources = new PagedModel<>(boards.getContent(), pageMetadata);
////        resources.add(linkTo(methodOn(BoardRestController.class).getBoards(pageable)).withSelfRel());
//                return ResponseEntity.ok(resources);
//    }

    // 버전 2 uri 제공
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)//반환값 json
    public ResponseEntity<PagedModel<EntityModel<Board>>> getBoards(@PageableDefault Pageable pageable, PagedResourcesAssembler<Board> assembler) {
        System.out.println("GET Access");

        Page<Board> boards = boardRepository.findAll(pageable);

        //링크 추가
        PagedModel<EntityModel<Board>> entityModels = assembler.toModel(boards);
        return ResponseEntity.ok(entityModels);
    }

    @PostMapping//생성
    public ResponseEntity<?> postBoard(@RequestBody Board board) {
        System.out.println("Post Access");
        board.setAnswerType(AnswerType.대기);
        board.setRegDate(LocalDateTime.now());
        boardRepository.save(board);

        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable("id")Long id) {
        //valid 체크
        boardRepository.deleteById(id);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}