package com.mandarin.petching.controller.qna;

import com.mandarin.petching.domain.Board;
import com.mandarin.petching.domain.Search;
import com.mandarin.petching.domain.SearchType;
import com.mandarin.petching.service.QnaService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QnaController {

    private final QnaService qnaService;

    @Autowired
    private Search search;

    @GetMapping({"", "/"})
    public String choice() {

        return "/qna/choice";
    }

    @GetMapping({"/famous"})
    public String famous() {
        return "/qna/famous";
    }

    @GetMapping({"/ask"})
    public String list(@PageableDefault Pageable pageable, Model model) {
        search.setType(SearchType.title);
        search.setKeyword("");
        model.addAttribute("boardList", this.qnaService.findQnaAllList(pageable));
        model.addAttribute("search", search);

        return "/qna/list";
    }

    @GetMapping({"/form"})
    public String form(@RequestParam(value = "id",defaultValue = "0") Long id, Model model) {
        model.addAttribute("board", this.qnaService.findBoardById(id));
        return "/qna/form";
    }


    @RequestMapping("/search")
    public String search(@RequestParam String searchType, @RequestParam String keyword, @PageableDefault Pageable pageable, Model model) { //header에  실려서 오는 json 문자열을 읽기 위해 사용

        search.setKeyword(keyword);
        search.setType(SearchType.valueOf(searchType));

        model.addAttribute("boardList", this.qnaService.search(search,pageable));
        model.addAttribute("search", search);

        return "/qna/list";
    }










//    @DeleteMapping("/deleteAll")
//    @Transactional
//    public String delete(@PageableDefault Pageable pageable, Model model) {
//        System.out.println("delete Access");
////        model.addAttribute("boardList", boardRepository.deleteByBoardTypeOrBoardTypeOrBoardType(BoardType.QnA문의1,BoardType.QnA문의2,BoardType.QnA문의3,pageable));
////        boardRepository.deleteByBoardTypeOrBoardTypeOrBoardType(String.valueOf(BoardType.QnA문의1),String.valueOf(BoardType.QnA문의2),String.valueOf(BoardType.QnA문의3),pageable);
////        boardRepository.deleteByBoardTypeOrBoardTypeOrBoardType(BoardType.QnA문의1.name(),BoardType.QnA문의2.name(),BoardType.QnA문의3.name(),pageable);
////        boardRepository.deleteByBoardTypeOrBoardTypeOrBoardType(BoardType.QnA문의1, BoardType.QnA문의2, BoardType.QnA문의3, pageable);
////        model.addAttribute("boardList", this.qnaService.deleteAll(BoardType.QnA문의1, BoardType.QnA문의2, BoardType.QnA문의3, pageable));
////        model.addAttribute("boardList", this.qnaService.deleteAll(BoardType.QnA문의1.toString(), BoardType.QnA문의2.toString(), BoardType.QnA문의3.toString(), pageable));
//
//        boardRepository.deleteByBoardTypeOrBoardTypeOrBoardType(BoardType.QnA문의1,BoardType.QnA문의2,BoardType.QnA문의3);
//
////        return "/qna/list";
//    }

}
