package com.mandarin.petching.controller;

import com.mandarin.petching.domain.Reservation;
import com.mandarin.petching.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/matching", method = {RequestMethod.POST})
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/profile/reservation/{petSitterId}")
    public String reservationForm() {
        return "reservation";
    }

    @PostMapping(value = {"/profile/reservation/match"})
    public String reservationMatch(Reservation reservation) {
        reservationService.createReservation(reservation);
        return "redirect:/";
    }
}