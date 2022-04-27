// code by. hyeok
package com.mandarin.petching.controller;

import com.mandarin.petching.domain.Member;
import com.mandarin.petching.domain.PetSitter;
import com.mandarin.petching.repository.MemberRepository;
import com.mandarin.petching.repository.PetSitterRepository;
import com.mandarin.petching.service.InfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
public class InfoController {

    @Autowired
    private InfoService infoService;

    private final MemberRepository memberRepository;
    private final PetSitterRepository petSitterRepository;

    @GetMapping("/matching/list")
    public String list(Model model, @PageableDefault(page = 0, size = 3, sort = "workingArea", direction = Sort.Direction.ASC) Pageable pageable,
                       String searchKeyword, String ableService) {

        Page<PetSitter> list = null;

        if (searchKeyword == null && ableService == null) {
            System.out.println("1. 초기 단계 일때");
            list = infoService.sitterList(pageable);
        } else if (ableService != null && searchKeyword == "") {
            System.out.println("2. 이용가능 서비스만 적용");
            list = infoService.findByAbleService(ableService, pageable);
            System.out.println(ableService);
            System.out.println(pageable);
        } else if (searchKeyword != "" && ableService == null) {
            System.out.println("3. 근무지역만 적용");
            list = infoService.sitterSearchList(searchKeyword, pageable);
        } else if (searchKeyword != "" && ableService != null) {
            System.out.println("4. 둘다 적용");
            list = infoService.findBySearchKeywordAndAbleServiceContaining(searchKeyword, ableService, pageable);
            System.out.println(list.getTotalElements());
//            list = infoService.sitterSearchList(searchKeyword, pageable);
//            list2 = infoService.findByAbleService(ableService, pageable);
            System.out.println("==============================================");
            System.out.println(searchKeyword);
            System.out.println(ableService);
            System.out.println("==============================================");

        }   else if (searchKeyword == "" && ableService == null) {
            System.out.println("5. 둘다 값이 없을때");
            list = infoService.sitterList(pageable);
        }
        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 2, 1);
        int endPage = Math.min(startPage+2, list.getTotalPages());
//        double startPage2 = Math.floor(list.getNumber())*10+1;
//        double endPage2;
//        double endPage3;
//        endPage = list.getTotalPages() > startPage+9 ? startPage+9 : list.getTotalPages();
//        endPage3 = endPage < startPage ? startPage : endPage;

//        int endPage = Math.min(nowPage + 5, list.getTotalPages());
        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "infolist";
    }

    @GetMapping("/matching/details")
    public String search(Authentication authentication, Model model, Long id) {
        String userEmail = authentication.getName();
        Member loginMember = memberRepository.findByUserEmail(userEmail);
        PetSitter petSitterMember= petSitterRepository.findById(id).get();
        model.addAttribute("petSitter", petSitterMember);
        model.addAttribute("petSitterMember", petSitterMember.getMember());
        model.addAttribute("loginMember", loginMember);
        return "details";
    }

}
