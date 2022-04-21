package com.mandarin.petching.controller.mypage;

import com.mandarin.petching.domain.*;
import com.mandarin.petching.dto.PetDto;
import com.mandarin.petching.repository.PetRepository;
import com.mandarin.petching.service.MyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final PetRepository petRepository;

    @GetMapping("/{memberId}/pet")
    public String petListView(@PathVariable Long memberId, Model model) {

        Member member = myPageService.findMemberById(memberId);

        List<Pet> petList = member.getPetList();

        model.addAttribute("petListEmpty", petList.isEmpty());
        model.addAttribute("petList", petList);
        model.addAttribute("memberId", memberId);

        return "mypage/petInfoList";
    }

    @GetMapping("{memberId}/pet/create")
    public String createPetForm(@PathVariable Long memberId, Model model) {

        GenderType[] genderTypes = GenderType.values();
        PetType[] petTypes = PetType.values();

        model.addAttribute("genderTypes", genderTypes);
        model.addAttribute("petTypes", petTypes);
        model.addAttribute("pet", new Pet());

        return "mypage/petInfoWrite";
    }

    @PostMapping("{memberId}/pet/create")
    public String createPet(@PathVariable Long memberId, PetDto petDto) {

        myPageService.createPet(memberId, petDto);

        return "redirect:/mypage/" + memberId + "/pet";
    }

    @GetMapping("/{memberId}/pet/{petId}")
    public String petView(@PathVariable Long memberId, @PathVariable Long petId, Model model) {

        Pet pet = myPageService.getPetById(petId);

        model.addAttribute("pet", pet);
        model.addAttribute("memberId", memberId);

        return "mypage/petInfoView";
    }

    @GetMapping("/{memberId}/pet/edit/{petId}")
    public String editPetForm(@PathVariable Long memberId, @PathVariable Long petId, Model model) {

        Pet pet = myPageService.getPetById(petId);

        GenderType[] genderTypes = GenderType.values();
        PetType[] petTypes = PetType.values();

        model.addAttribute("pet", pet);
        model.addAttribute("genderTypes", genderTypes);
        model.addAttribute("petTypes", petTypes);

        return "mypage/petInfoEdit";
    }

    @PostMapping("/{memberId}/pet/edit/{petId}")
    public String editPet(@PathVariable Long memberId, @PathVariable Long petId, PetDto petDto, Model model) {

        // TODO Bean Validation

        myPageService.updatePet(petId, petDto);

        return "redirect:/mypage/" + memberId + "/pet";
    }
}
