package com.mandarin.petching.controller.mypage;

import com.mandarin.petching.domain.*;
import com.mandarin.petching.repository.MemberRepository;
import com.mandarin.petching.service.MyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/mypage/pet")
@RequiredArgsConstructor
public class PetInfoController {

    private final MyPageService myPageService;
    private final MemberRepository memberRepository;

    @GetMapping
    public String petListView(Authentication authentication, Model model) {

        String userName = authentication.getName();
        Member member = memberRepository.findByUserEmail(userName);

        List<Pet> petList = member.getPetList();

        model.addAttribute("petListEmpty", petList.isEmpty());
        model.addAttribute("petList", petList);

        return "mypage/petInfoList";
    }

    @GetMapping("/create")
    public String createPetForm(Model model) {

        GenderType[] genderTypes = GenderType.values();
        PetType[] petTypes = PetType.values();

        model.addAttribute("genderTypes", genderTypes);
        model.addAttribute("petTypes", petTypes);
        model.addAttribute("pet", new Pet());

        return "mypage/petInfoWrite";
    }

    @PostMapping("/create")
    public String createPet(Authentication authentication, @Validated Pet pet, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            GenderType[] genderTypes = GenderType.values();
            PetType[] petTypes = PetType.values();

            model.addAttribute("genderTypes", genderTypes);
            model.addAttribute("petTypes", petTypes);

            return "mypage/petInfoWrite";
        }

        String userName = authentication.getName();
        Member member = memberRepository.findByUserEmail(userName);

        myPageService.createPet(member, pet);

        return "redirect:/mypage/pet";
    }

    @GetMapping("/{petId}")
    public String petView(@PathVariable Long petId, Model model) {

        Pet pet = myPageService.getPetById(petId);

        model.addAttribute("pet", pet);
        model.addAttribute("isReserved", pet.getReservationList().isEmpty());

        return "mypage/petInfoView";
    }

    @GetMapping("/edit/{petId}")
    public String editPetForm(@PathVariable Long petId, Model model) {

        Pet pet = myPageService.getPetById(petId);

        GenderType[] genderTypes = GenderType.values();
        PetType[] petTypes = PetType.values();

        model.addAttribute("pet", pet);
        model.addAttribute("genderTypes", genderTypes);
        model.addAttribute("petTypes", petTypes);

        return "mypage/petInfoEdit";
    }

    @PostMapping("/edit/{petId}")
    public String editPet(@PathVariable Long petId,
                          @Validated Pet pet,
                          BindingResult bindingResult,
                          Model model) {

        if (bindingResult.hasErrors()) {

            GenderType[] genderTypes = GenderType.values();
            PetType[] petTypes = PetType.values();

            model.addAttribute("genderTypes", genderTypes);
            model.addAttribute("petTypes", petTypes);

            return "mypage/petInfoEdit";
        }

        myPageService.updatePet(petId, pet);

        return "redirect:/mypage/pet";
    }

    @PostMapping("/delete/{petId}")
    public String deletePet(@PathVariable Long petId) {

        myPageService.deletePet(petId);

        return "redirect:/mypage/pet";
    }
}
