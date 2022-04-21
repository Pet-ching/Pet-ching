package com.mandarin.petching.controller.mypage;

import com.mandarin.petching.domain.*;
import com.mandarin.petching.dto.PetDto;
import com.mandarin.petching.repository.MemberRepository;
import com.mandarin.petching.service.MyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class PetInfoController {

    private final MyPageService myPageService;
    private final MemberRepository memberRepository;

    @GetMapping("/pet")
    public String petListView(Authentication authentication, Model model) {

        String userName = authentication.getName();
        Member member = memberRepository.findByUserEmail(userName);

        List<Pet> petList = member.getPetList();

        model.addAttribute("petListEmpty", petList.isEmpty());
        model.addAttribute("petList", petList);

        return "mypage/petInfoList";
    }

    @GetMapping("/pet/create")
    public String createPetForm(Model model) {

        GenderType[] genderTypes = GenderType.values();
        PetType[] petTypes = PetType.values();

        model.addAttribute("genderTypes", genderTypes);
        model.addAttribute("petTypes", petTypes);
        model.addAttribute("pet", new Pet());

        return "mypage/petInfoWrite";
    }

    @PostMapping("/pet/create")
    public String createPet(Authentication authentication, PetDto petDto) {

        String userName = authentication.getName();
        Member member = memberRepository.findByUserEmail(userName);

        myPageService.createPet(member, petDto);

        return "redirect:/mypage/pet";
    }

    @GetMapping("/pet/{petId}")
    public String petView(@PathVariable Long petId, Model model) {

        Pet pet = myPageService.getPetById(petId);

        model.addAttribute("pet", pet);

        return "mypage/petInfoView";
    }

    @GetMapping("pet/edit/{petId}")
    public String editPetForm(@PathVariable Long petId, Model model) {

        Pet pet = myPageService.getPetById(petId);

        GenderType[] genderTypes = GenderType.values();
        PetType[] petTypes = PetType.values();

        model.addAttribute("pet", pet);
        model.addAttribute("genderTypes", genderTypes);
        model.addAttribute("petTypes", petTypes);

        return "mypage/petInfoEdit";
    }

    @PostMapping("/pet/edit/{petId}")
    public String editPet(@PathVariable Long petId, PetDto petDto, Model model) {

        // TODO Bean Validation

        myPageService.updatePet(petId, petDto);

        return "redirect:/mypage/pet";
    }
}
