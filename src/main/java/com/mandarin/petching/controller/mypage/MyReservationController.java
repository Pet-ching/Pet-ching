package com.mandarin.petching.controller.mypage;

import com.mandarin.petching.domain.Member;
import com.mandarin.petching.domain.Pet;
import com.mandarin.petching.domain.Reservation;
import com.mandarin.petching.repository.MemberRepository;
import com.mandarin.petching.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyReservationController {

    private final ReservationService reservationService;
    private final MemberRepository memberRepository;

    @GetMapping("/pet/reservation")
    public String PetOwnerReservationList(Authentication authentication, Model model) {

        String userName = authentication.getName();
        Member member = memberRepository.findByUserEmail(userName);

        List<Reservation> reservationList = new ArrayList<>();

        List<Pet> petList = member.getPetList();
        for (Pet pet : petList) {
            for (Reservation reservation : pet.getReservationList()) {
                reservationList.add(reservation);
            }
        }

        model.addAttribute("reservationList", reservationList);

        return "mypage/petReservation";
    }

    @GetMapping("/petsitter/reservation")
    public String PetSitterReservationList(@PathVariable Long memberId, Model model) {

        // petSitter 예약 현황 조회
        return "mypage/reservation";
    }

    @GetMapping("/reservation/{reservationId}")
    public String reservationView(@PathVariable Long memberId, @PathVariable Long reservationId, Model model) {

        // 해당 예약 현황 가져와서 모델에 담아주기
        Reservation reservation = reservationService.findReservationById(reservationId);
        model.addAttribute(reservation);

        return "mypage/reservationDetail";
    }
}
