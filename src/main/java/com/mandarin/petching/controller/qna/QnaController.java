package com.mandarin.petching.controller.qna;

import com.mandarin.petching.service.QnaService;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/qna")
public class QnaController {

    private final QnaService qnaService;

    public QnaController(QnaService qnaService) {
        this.qnaService = qnaService;
    }

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
        model.addAttribute("boardList", this.qnaService.findBoardList(pageable));
        return "/qna/list";
    }

    @GetMapping({"/form"})
    public String form(@RequestParam(value = "id",defaultValue = "0") Long id, Model model) {
        model.addAttribute("board", this.qnaService.findBoardById(id));
        return "/qna/form";
    }



}
