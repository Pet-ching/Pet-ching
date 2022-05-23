package com.mandarin.petching.controller.admin;

import com.mandarin.petching.domain.Member;
import com.mandarin.petching.domain.Pet;
import com.mandarin.petching.domain.Reservation;
import com.mandarin.petching.domain.Role;
import com.mandarin.petching.dto.*;
import com.mandarin.petching.repository.MemberRepository;
import com.mandarin.petching.service.AdminKgyService;
import com.mandarin.petching.service.MemberService;
import com.mandarin.petching.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminKgyController {

    private final AdminKgyService adminKgyService;

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
        int endPage = Math.min(startPage + 2, list.getTotalPages());

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
        int endPage = Math.min(startPage + 2, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        Page<ReservationDTO> page = adminKgyService.getAllReservationPage(pageable);
        model.addAttribute("boardList", page);
        return "admin/reservations";
    }

    //선택화면
    @GetMapping({"", "/"})
    public String choice() {

        return "/qna/choice";
    }

    //가격 통계 정보
    @GetMapping("/petsitterchart")
    public String petsitterchart(Model model) {
        //소-중-대(견) -> 고양이 -> 기타 동물 순
        //총 수, 최댓값, 최솟값, 평균
        MathDTO smallDog = adminKgyService.getPetSitterPrice().get(0);
        MathDTO middleDog = adminKgyService.getPetSitterPrice().get(1);
        MathDTO largeDog = adminKgyService.getPetSitterPrice().get(2);
        MathDTO cat = adminKgyService.getPetSitterPrice().get(3);
        MathDTO etc = adminKgyService.getPetSitterPrice().get(4);

        model.addAttribute("smallDog", smallDog);
        model.addAttribute("middleDog", middleDog);
        model.addAttribute("largeDog", largeDog);
        model.addAttribute("cat", cat);
        model.addAttribute("etc", etc);

        //가격 별로 인원수
        //소-중-대(견) -> 고양이 -> 기타 동물 순
        List<CountByNumDTO> smallDogCountByPrice = adminKgyService.getPetSitterCountByPrice().get(0);
        List<CountByNumDTO> middleDogCountByPrice = adminKgyService.getPetSitterCountByPrice().get(1);
        List<CountByNumDTO> largeDogCountByPrice = adminKgyService.getPetSitterCountByPrice().get(2);
        List<CountByNumDTO> catCountByPrice = adminKgyService.getPetSitterCountByPrice().get(3);
        List<CountByNumDTO> etcCountByPrice = adminKgyService.getPetSitterCountByPrice().get(4);

        model.addAttribute("smallDogCountByPrice", smallDogCountByPrice);
        model.addAttribute("middleDogCountByPrice", middleDogCountByPrice);
        model.addAttribute("largeDogCountByPrice", largeDogCountByPrice);
        model.addAttribute("catCountByPrice", catCountByPrice);
        model.addAttribute("etcCountByPrice", etcCountByPrice);

        // 지역별 매칭 정보
        List<AreaDTO> list = adminKgyService.getMatchingArea();
        model.addAttribute("area", list);

        //자격증 정보
        List<CertificateDTO> certificateList = adminKgyService.getCountByCertificate();
        model.addAttribute("certificate", certificateList);

        return "/admin/petsitterchart";
    }


    @GetMapping("/petownerchart")
    public String petOwnerInfo(Model model) {
        List<PetOwnerDTO> age = adminKgyService.getPetOwnerAgeList();
        List<PetOwnerDTO> residence = adminKgyService.getPetOwnerResidenceList();
        List<CountByStringDTO> count = adminKgyService.getPetCountByPetOwnerList();
        model.addAttribute("age", age);
        model.addAttribute("residence", residence);
        model.addAttribute("count", count);

        return "/admin/petownerchart";
    }

    @GetMapping("/petchart")
    public String petInfo(Model model) {

        List<PetDTO> pet = adminKgyService.getAllPetList();
        model.addAttribute("pet", pet);

        // 펫 카운드
        List<CountByNumDTO> age = adminKgyService.getPetCountByAgeList();
        model.addAttribute("numDTO", age);

        List<CountByBooleanDTO> neu = adminKgyService.getPetCountByNeutralizationList();
        model.addAttribute("boolDTO", neu);

        List<CountByPetTypeDTO> type = adminKgyService.getPetCountByPetTypeList();
        model.addAttribute("petTypeDTO", type);


        return "/admin/petchart";
    }

    @GetMapping("/deny")
    public String deny() {

        return "/testAdmin/deny";
    }
}
