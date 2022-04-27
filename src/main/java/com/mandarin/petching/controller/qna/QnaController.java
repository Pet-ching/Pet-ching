package com.mandarin.petching.controller.qna;

import com.mandarin.petching.domain.*;
import com.mandarin.petching.dto.BoardDto;
import com.mandarin.petching.dto.ImagesDto;
import com.mandarin.petching.repository.BoardRepository;
import com.mandarin.petching.repository.ImagesRepository;
import com.mandarin.petching.repository.MemberRepository;
import com.mandarin.petching.service.ImagesService;
import com.mandarin.petching.service.QnaService;
import com.mandarin.petching.util.MD5Generator;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.*;
import java.time.LocalDateTime;


@Controller
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QnaController {

    private final QnaService qnaService;
    private final MemberRepository memberRepository;
    private final ImagesService imagesService;
    private final ImagesRepository imagesRepository;
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

    @PostMapping("/images")
    public String write(Authentication authentication, @RequestParam("file") MultipartFile files, @PageableDefault Pageable pageable, Model model) {

        String lootPath = System.getProperty("user.dir");
        try {
                String origFilename = files.getOriginalFilename();
                String filename = new MD5Generator(origFilename).toString();
                /* 실행되는 위치의 'files' 폴더에 파일이 저장됩니다. */
                //System.getProperty("user.dir") 프로젝트 경로
                String savePath = lootPath + "\\src\\main\\resources\\static\\files";
                /* 파일이 저장되는 폴더가 없으면 폴더를 생성합니다. */
                //new File(경로, 이름);
                if (!new File(savePath).exists()) {
                    try{
                        new File(savePath).mkdir();//디렉토리 생성
                    }
                    catch(Exception e){
                        e.getStackTrace();
                    }
            }

                if(filename.length()>0) {

                    //json 파일 읽기
                    JSONParser parser = new JSONParser();
                    Reader reader = new FileReader(lootPath + "\\src\\main\\resources\\static\\json\\petchingBoard.json");
                    JSONObject jsonObject = (JSONObject) parser.parse(reader);

                    //json파일에서 id부분 get하기
                    long id = (Long) jsonObject.get("id");
                    Board board = boardRepository.getById(id);

                    //파일 경로 지정
                    String filePath = savePath + "\\" + filename;
                    files.transferTo(new File(filePath));

                    ImagesDto imagesDto = new ImagesDto();
                    imagesDto.setImgName(origFilename);
                    imagesDto.setServerImgName(filename);
                    imagesDto.setImgPath(filePath);
                    imagesDto.setImgRegDate(LocalDateTime.now());
                    imagesDto.setImageType(ImageType.Qna);
                    imagesDto.setBoard(board);

                    imagesService.saveImage(imagesDto);
                }

        } catch(Exception e) {
            e.printStackTrace();
        }


        return "redirect:/qna/ask";
    }






}
