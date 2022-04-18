package com.mandarin.petching.controller.qna;

import com.mandarin.petching.service.QnaService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/qna")
public class QnaController {


    private final QnaService qnaService;

    public QnaController(QnaService qnaService) {
        this.qnaService = qnaService;
    }

    @GetMapping({"", "/"})
    public String choice()
    {
        return "/qna/choice";
    }
//    public String board(@RequestParam(value = "idx", defaultValue = "0") Long id, Model model) {
//        model.addAttribute("board", qnaService.findBoardById(id));
//        return "/board/form";
//    }
    @GetMapping("/famous")
    public String famous()
    {
        return "/qna/famous";
    }

    @GetMapping("/with")
    public String list(@PageableDefault Pageable pageable, Model model) {
        model.addAttribute("boardList", qnaService.findBoardList(pageable));
        return "/qna/with";
    }

}
