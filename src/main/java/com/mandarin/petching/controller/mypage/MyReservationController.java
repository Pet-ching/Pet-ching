package com.mandarin.petching.controller.mypage;

import com.mandarin.petching.domain.Member;
import com.mandarin.petching.domain.PetSitter;
import com.mandarin.petching.domain.Reservation;
import com.mandarin.petching.repository.MemberRepository;
import com.mandarin.petching.repository.PetSitterRepository;
import com.mandarin.petching.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyReservationController {

    private final ReservationService reservationService;
    private final MemberRepository memberRepository;
    private final PetSitterRepository petSitterRepository;

    @GetMapping("/pet/reservation")
    public String PetOwnerReservationList(Authentication authentication, Model model) {

        Member member = getMember(authentication);

        List<Reservation> reservationList = reservationService.findByPetOwner(member.getId());

        model.addAttribute("reservationList", reservationList);
        model.addAttribute("reservationEmpty", reservationList.isEmpty());

        return "mypage/reservationList";
    }

    @GetMapping("/petsitter/reservation")
    public String PetSitterReservationList(Authentication authentication, Model model) {

        Member member = getMember(authentication);

        List<Reservation> reservationList = reservationService.findByPetSitter(member.getPetSitter().getId());

        model.addAttribute("reservationList", reservationList);
        model.addAttribute("reservationEmpty", reservationList.isEmpty());

        return "mypage/petSitterReservationList";
    }

    @GetMapping("/reservation/{reservationId}")
    public String reservationView(@PathVariable Long reservationId, Model model) {

        Reservation reservation = reservationService.findReservationById(reservationId);

        PetSitter petSitter = petSitterRepository.findById(reservation.getPetSitterId()).get();
        Member petOwner = memberRepository.findById(reservation.getPetOwnerId()).get();

        model.addAttribute("reservation", reservation);
        model.addAttribute("petSitterName", petSitter.getMember().getUserName());
        model.addAttribute("petOwnerName", petOwner.getUserName());

        return "mypage/reservationDetail";
    }

    @GetMapping("/reservation/approval/{reservationId}")
    public String approvReservation(@PathVariable Long reservationId) {

        reservationService.approveReservation(reservationId);

        return "redirect:/mypage/petsitter/reservation";
    }

    @GetMapping("/reservation/refuse/{reservationId}")
    public String refuseReservation(@PathVariable Long reservationId) {

        reservationService.refuseReservation(reservationId);

        return "redirect:/mypage/petsitter/reservation";
    }

    @GetMapping("/reservation/cancel/{reservationId}")
    public String cancelReservation(@PathVariable Long reservationId) {

       reservationService.cancelReservation(reservationId);

       return "redirect:/mypage/pet/reservation";
    }

    private Member getMember(Authentication authentication) {
        String userName = authentication.getName();
        return memberRepository.findByUserEmail(userName);
    }
}
