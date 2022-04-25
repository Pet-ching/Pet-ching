package com.mandarin.petching.controller;

import com.mandarin.petching.domain.Board;
import com.mandarin.petching.domain.Member;
import com.mandarin.petching.repository.BoardRepository;
import com.mandarin.petching.repository.MemberRepository;
import com.mandarin.petching.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                       @RequestParam(required = false, defaultValue = "") String searchText) {
        //Page<Board> boards = boardRepository.findAll(pageable);
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText, searchText, pageable);
        int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4);
        int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 4);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("boards", boards);
        return "board/list";
    }

    @GetMapping("/read")
    public String read(Model model, @RequestParam(required = false) Long id , Authentication authentication) {
        if (id == null) {
            model.addAttribute("board", new Board());
        } else {
            Board board = boardRepository.findById(id).orElse(null);
            model.addAttribute("board", board);
            String userName = authentication.getName();
            Member member = memberRepository.findByUserEmail(userName);
            model.addAttribute("writer", member.getId() == board.getMember().getId());
        }


        return "board/read";
    }

    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id ) {
        if (id == null) {
            model.addAttribute("board", new Board());
            model.addAttribute("localDateTime", LocalDateTime.now());
        } else {
            Board board = boardRepository.findById(id).orElse(null);
            model.addAttribute("board", board);
        }

        return "board/form";
    }

    @PostMapping("/form")
    public String greetingSubmit(@Valid Board board, BindingResult bindingResult, Authentication authentication) {
        if (bindingResult.hasErrors()) {
            return "board/form";
        }

        String userName = authentication.getName();
//        Member member = memberRepository.findByUserEmail(userName);
        boardService.save(userName, board);
        //boardRepository.save(board);
        return "redirect:/board/list";
    }
}
