package com.mandarin.petching.controller.qna;

import com.mandarin.petching.domain.*;
import com.mandarin.petching.dto.ImagesDto;
import com.mandarin.petching.repository.BoardRepository;
import com.mandarin.petching.repository.ImagesRepository;
import com.mandarin.petching.repository.MemberRepository;
import com.mandarin.petching.repository.PetSitterRepository;
import com.mandarin.petching.service.ImagesService;
import com.mandarin.petching.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;


@Controller
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QnaController {

    private final QnaService qnaService;
    private final MemberRepository memberRepository;
    private final ImagesService imagesService;
    private final BoardRepository boardRepository;
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

        //검색어 환경 초기화
        search.setType(SearchType.title);
        search.setKeyword("");

        model.addAttribute("boardList", this.qnaService.findQnaAllList(member, pageable));
        model.addAttribute("member", member);
        model.addAttribute("search", search);

        return "/qna/list";
    }

    //상세 보기
    @GetMapping({"/form"})
    public String form(Authentication authentication, @RequestParam(value = "id",defaultValue = "0") Long id, Model model) throws IOException {
        String userName = authentication.getName();
        Member member = memberRepository.findByUserEmail(userName);
        Board board = this.qnaService.findBoardById(id, member);

        if (board.getMember() == member || member.getRole().name().equals(Role.ADMIN.name())) {
            //Role.USER일 경우 들어올려고 this.qnaService.findBoardById(id, member); 에서 member로 강제 설정했다.
            board.setMember(null);

            model.addAttribute("board", board);
            model.addAttribute("image",this.imagesService.findOneByBoard(board));

            return "/qna/form";

        } else {

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
            model.addAttribute("image",this.imagesService.findOneByBoard(board));

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


    //파일 업로드
    //저장했을 때
    @PostMapping("/images")
    public String createFile(Authentication authentication, @RequestParam("file") MultipartFile files, @PageableDefault Pageable pageable, Model model) {

        ImagesDto imagesDto= imagesService.storeFile(authentication,files,pageable,model);

        //파일 제목으로 빈 파일 생성 막기
        if(imagesDto.getImgName() != null && imagesDto.getImgName() != "" && imagesDto.getImgName().length()>0) {
            imagesService.saveImage(imagesDto);
        }

        return "redirect:/admin/reservations";
    }


    //수정했을 때
    @PostMapping("/images/{id}")//수정
    public String updateFile(Authentication authentication, @RequestParam("file") MultipartFile files, @PageableDefault Pageable pageable, Model model, @PathVariable("id")Long id) {

        ImagesDto imagesDto= imagesService.storeFile(authentication,files,pageable,model);
        //boardId 넣기
        imagesDto.setBoard(boardRepository.getById(id));

        //파일 제목으로 빈 파일 생성 막기
        if(imagesDto.getImgName() != null && imagesDto.getImgName() != "" && imagesDto.getImgName().length()>0) {
            imagesService.saveImage(imagesDto);
        }


        return "redirect:/admin/reservations";

    }





}
