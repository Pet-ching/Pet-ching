package com.mandarin.petching.controller;

import com.mandarin.petching.domain.*;
import com.mandarin.petching.repository.PetOwnerRepository;
import com.mandarin.petching.service.MyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;
    private final PetOwnerRepository petOwnerRepository;

    @GetMapping
    public String mypage(Model model) {

        // TODO 로그인 유저 정보
        model.addAttribute("memberId", 1L);

        return "mypage/mypage";
    }

    @GetMapping("/{memberId}/petowner")
    public String petOwnerView(@PathVariable Long memberId, Model model) {

        Member member = myPageService.findMemberById(memberId);

        try {
            PetOwner petOwner = member.getPetOwner();

            if (petOwner == null) {
                return "myPage/temp";
            }

            model.addAttribute("petOwner", petOwner);
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
            PetOwner petOwner = member.getPetOwner();

            if (petOwner == null) {
                model.addAttribute("petOwner", new PetOwner());
            } else {
                model.addAttribute("petOwner", petOwner);
            }

        } catch (EntityNotFoundException e) {
            model.addAttribute("petOwner", new PetOwner());
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

        myPageService.savePetOwner(memberId, petOwnerDto);

        return "redirect:/mypage/" + memberId + "/petowner";
    }
}
