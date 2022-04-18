package com.mandarin.petching.controller;

import com.mandarin.petching.domain.PetSitter;
import com.mandarin.petching.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/matching")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping(value = {"/profile/{petSitterId}"})
    public String viewProfile(@PathVariable Long petSitterId, Model model) {
        PetSitter petSitter = profileService.findPetSitter(petSitterId);
        model.addAttribute("petSitter", petSitter);
        return "profile"; //profile.html 호출
    }

    @GetMapping(value = {"/map"})
    public String map(){
        return "map";
    }
}
