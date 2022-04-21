// code by. hyeok
package com.mandarin.petching.controller;

import com.mandarin.petching.domain.Member;
import com.mandarin.petching.domain.PetSitter;
import com.mandarin.petching.repository.MemberRepository;
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
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class InfoController {

    @Autowired
    private InfoService infoService;

    private final MemberRepository memberRepository;


    @GetMapping("/matching/list")
    public String list(Model model, @PageableDefault(page = 0, size = 10, sort = "workingArea", direction = Sort.Direction.ASC) Pageable pageable, String searchKeyword, String ableService) {

        Page<PetSitter> list = null;
        Page<PetSitter> list2 = null;

        if (searchKeyword == null && ableService == null) {
            System.out.println("1. 초기 단계 일때");
            list = infoService.sitterList(pageable);
        } else if (ableService != null && searchKeyword == "") {
            System.out.println("2. 이용가능 서비스만 적용");
            list = infoService.findByAbleService(ableService.replace(",", " "), pageable);
            System.out.println(ableService.replace(",", " "));
            System.out.println(pageable);
        } else if (searchKeyword != "" && ableService == null) {
            System.out.println("3. 근무지역만 적용");
            list = infoService.sitterSearchList(searchKeyword, pageable);
        } else if (searchKeyword != "" && ableService != null) {
            System.out.println("4. 둘다 적용");
            list = infoService.findBySearchKeywordAndAbleServiceContaining(searchKeyword, ableService.replace(",", " "), pageable);
            System.out.println(list.getTotalElements());
//            list = infoService.sitterSearchList(searchKeyword, pageable);
//            list2 = infoService.findByAbleService(ableService, pageable);
            System.out.println("==============================================");
            System.out.println(searchKeyword);
            System.out.println(ableService.replace(",", " "));
            System.out.println("==============================================");

        }   else if (searchKeyword == "" && ableService == null) {
            System.out.println("5. 둘다 값이 없을때");
            list = infoService.sitterList(pageable);
        }
        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("list2", list2);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "infolist";
    }

    @GetMapping("/matching/details")
    public String search(Authentication authentication, Model model, Long id) {
        String userEmail = authentication.getName();
        Member loginMember = memberRepository.findByUserEmail(userEmail);
        Member petSitterMember= memberRepository.findById(id).get();
        model.addAttribute("petSitterMember", petSitterMember);
        model.addAttribute("petSitter", petSitterMember.getPetSitter());
        model.addAttribute("loginMember", loginMember);
        return "details";
    }


//    @GetMapping("/maching/list/{area}")
//    public String search(Model model, String area) {
//        model.addAttribute("area", infoService.area);
//        return "infolist";
//    }

}
