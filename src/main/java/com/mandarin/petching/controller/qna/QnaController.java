package com.mandarin.petching.controller.qna;

import com.mandarin.petching.domain.*;
import com.mandarin.petching.repository.MemberRepository;
import com.mandarin.petching.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@Controller
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QnaController {

    private final QnaService qnaService;
    private final MemberRepository memberRepository;

    @Autowired
    private Search search;


    //선택화면
    @GetMapping({"", "/"})
    public String choice() {

        return "/qna/choice";
    }

    //자주 찾는 질문
    @GetMapping({"/famous"})
    public String famous() {
        return "/qna/famous";
    }

    //1:1문의 게시판
    @GetMapping({"/ask"})
    public String list(Authentication authentication, @PageableDefault Pageable pageable, Model model) {

        String userName = authentication.getName();
        Member member = memberRepository.findByUserEmail(userName);

        search.setType(SearchType.title);
        search.setKeyword("");

        model.addAttribute("boardList", this.qnaService.findQnaAllList(member, pageable));
        model.addAttribute("member", member);
        model.addAttribute("search", search);



        return "/qna/list";
    }

    @GetMapping({"/form"})
    public String form(Authentication authentication, @RequestParam(value = "id",defaultValue = "0") Long id, Model model) throws IOException {
        String userName = authentication.getName();
        Member member = memberRepository.findByUserEmail(userName);

        Board board = this.qnaService.findBoardById(id, member);
        if(board.getMember() == member || member.getRole().name().equals(Role.ADMIN.name()) ) {
            board.setMember(null);
            model.addAttribute("board", board);
            return "/qna/form";
        }
        else
        {

            return "/qna/error";
        }

    }

        @GetMapping("/answer")
    public String answer(Authentication authentication, @RequestParam(value = "id",defaultValue = "0") Long id, Model model) throws IOException {

        String userName = authentication.getName();
        Member member = memberRepository.findByUserEmail(userName);
        Board board = this.qnaService.findBoardById(id,member);
        Reply reply = this.qnaService.reply(board);

        if(board.getMember() == member|| member.getRole().name().equals(Role.ADMIN.name())) {
            model.addAttribute("board", this.qnaService.findBoardById(id, member));
            model.addAttribute("member", member);
            model.addAttribute("reply", reply);

            return "/qna/answer";
        }
        else
        {
            return "/qna/error";
        }
    }


    @RequestMapping("/search")
    public String search(Authentication authentication, @RequestParam String searchType, @RequestParam String keyword, @PageableDefault Pageable pageable, Model model) { //header에  실려서 오는 json 문자열을 읽기 위해 사용
        String userName = authentication.getName();
        Member member = memberRepository.findByUserEmail(userName);

        search.setKeyword(keyword);
        search.setType(SearchType.valueOf(searchType));

        model.addAttribute("boardList", this.qnaService.search(search,member, pageable));
        model.addAttribute("member", member);
        model.addAttribute("search", search);

        return "/qna/list";
    }






}
