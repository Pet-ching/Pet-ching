package com.mandarin.petching.controller.mypage;

import com.mandarin.petching.domain.*;
import com.mandarin.petching.repository.PetRepository;
import com.mandarin.petching.service.MyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@Slf4j
@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class PetInfoController {

    private final MyPageService myPageService;
    private final PetRepository petRepository;

    @GetMapping("/{memberId}/petowner")
    public String petOwnerView(@PathVariable Long memberId, Model model) {

        Member member = myPageService.findMemberById(memberId);

        try {
            Pet pet = member.getPetList().get(1);

            if (pet == null) {
                return "myPage/temp";
            }

            model.addAttribute("petOwner", pet);
            model.addAttribute("memberId", 1L);

            return "mypage/petOwner";
        } catch (EntityNotFoundException e) {
            return "myPage/temp";
        }
    }

    @GetMapping("/{memberId}/petowner/edit")
    public String petOwnerEditForm(@PathVariable Long memberId, Model model) {

        Member member = myPageService.findMemberById(memberId);

        try {
            Pet pet = member.getPetList().get(1);

            if (pet == null) {
                model.addAttribute("pet", new Pet());
            } else {
                model.addAttribute("pet", pet);
            }

        } catch (EntityNotFoundException e) {
            model.addAttribute("pet", new Pet());
        }

        GenderType[] genderTypes = GenderType.values();
        PetType[] petTypes = PetType.values();

        model.addAttribute("genderTypes", genderTypes);
        model.addAttribute("petTypes", petTypes);

        return "mypage/petOwnerEdit";
    }

    @PostMapping("/{memberId}/petowner/edit")
    public String petOwnerEdit(@PathVariable Long memberId, PetOwnerDTO petOwnerDto, Model model) {

        // TODO Bean Validation

        myPageService.savePet(memberId, petOwnerDto);

        return "redirect:/mypage/" + memberId + "/petowner";
    }
}
