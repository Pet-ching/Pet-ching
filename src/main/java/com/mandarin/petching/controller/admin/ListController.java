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


    private final MemberService memberService;

    private final ReservationService reservationService;

    private final MemberRepository memberRepository;


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