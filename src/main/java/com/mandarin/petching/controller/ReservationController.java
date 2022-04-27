package com.mandarin.petching.controller;

import com.mandarin.petching.domain.Member;
import com.mandarin.petching.domain.Pet;
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
@RequestMapping(value = "/matching", method = {RequestMethod.POST})
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final MemberRepository memberRepository;
    private final PetSitterRepository petSitterRepository;

    @GetMapping("/profile/reservation/{petSitter_id}")
    public String reservationForm(Authentication authentication, Model model, @PathVariable Long petSitter_id) {
        String userEmail = authentication.getName();
        Member member = memberRepository.findByUserEmail(userEmail);
        PetSitter petSitter = petSitterRepository.findById(petSitter_id).get();
        List<Pet> petList = member.getPetList();

        model.addAttribute("petOwner", member);
        model.addAttribute("petSitter", petSitter);
        model.addAttribute("petList", petList);
        model.addAttribute("petListEmpty", petList.isEmpty());

        return "reservation";
    }

    @PostMapping(value = {"/profile/reservation/match"})
    public String reservationMatch(Reservation reservation) {
        reservationService.createReservation(reservation);
        return "redirect:/";
    }
}