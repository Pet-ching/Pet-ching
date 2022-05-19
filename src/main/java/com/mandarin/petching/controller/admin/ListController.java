package com.mandarin.petching.controller.admin;

import com.mandarin.petching.domain.Member;
import com.mandarin.petching.domain.Reservation;
import com.mandarin.petching.repository.MemberRepository;
import com.mandarin.petching.service.MemberService;
import com.mandarin.petching.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class ListController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private ReservationService reservationService;

    private final MemberRepository memberRepository;

    @GetMapping("/members")
    public String list(Model model, @PageableDefault(page = 0, size = 5, sort = "userName") Pageable pageable) {

        Page<Member> list = memberService.memberList(pageable);
        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 2, 1);
        int endPage = Math.min(startPage+2, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "admin/members";
    }

    @GetMapping("/reservations")
    public String reservationList(Model model, @PageableDefault(page = 0, size = 5, sort = "startDate") Pageable pageable) {

        Page<Reservation> list = reservationService.reservationList(pageable);
        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 2, 1);
        int endPage = Math.min(startPage+2, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "admin/reservations";
    }

    @GetMapping("/petchart")
    public String petChartList() {
        return "admin/petchart";
    }

    @GetMapping("/petsitterchart")
    public String petSitterChartList() {
        return "admin/petsitterchart";
    }

    @GetMapping("/petownerchart")
    public String petOwnerChartList() {
        return "admin/petownerchart";
    }


}
