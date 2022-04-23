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
import java.util.Optional;

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

        List<Reservation> reservationList = reservationService.findByPetOwner(member.getId());

        model.addAttribute("reservationList", reservationList);

        return "mypage/reservationList";
    }

    @GetMapping("/petsitter/reservation")
    public String PetSitterReservationList(Authentication authentication, Model model) {

        String userName = authentication.getName();
        Member member = memberRepository.findByUserEmail(userName);

        List<Reservation> reservationList = reservationService.findByPetSitter(member.getId());

        model.addAttribute("reservationList", reservationList);

        return "mypage/reservationList";
    }

    @GetMapping("/reservation/{reservationId}")
    public String reservationView(@PathVariable Long reservationId, Model model) {

        Reservation reservation = reservationService.findReservationById(reservationId);
        Member petSitter = memberRepository.findById(reservation.getPetSitterId()).get();
        Member petOwner = memberRepository.findById(reservation.getPetOwnerId()).get();

        model.addAttribute("reservation", reservation);
        model.addAttribute("petSitterName", petSitter.getUserName());
        model.addAttribute("petOwnerName", petOwner.getUserName());

        return "mypage/reservationDetail";
    }
}
