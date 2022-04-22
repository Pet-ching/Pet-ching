package com.mandarin.petching.controller.mypage;

import com.mandarin.petching.domain.*;
import com.mandarin.petching.repository.MemberRepository;
import com.mandarin.petching.service.MyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/mypage/petsitter")
@RequiredArgsConstructor
public class PetSitterInfoController {

    private final MyPageService myPageService;
    private final MemberRepository memberRepository;

    @GetMapping
    public String petSitterInfoView(Authentication authentication, Model model) {

        String userName = authentication.getName();
        Member member = memberRepository.findByUserEmail(userName);

        PetSitter petSitter = member.getPetSitter();

        model.addAttribute("petSitterEmpty", petSitter == null);
        model.addAttribute("petSitter", petSitter);

        return "mypage/petSitterInfo";
    }

    @GetMapping("/create")
    public String createPetForm(Model model) {

        model.addAttribute("petSitter", new PetSitter());
        model.addAttribute("feeList", new FeeList());
        model.addAttribute("workingDayAndTime", new WorkingDayAndTime());
        model.addAttribute("certificateList", CertificateType.getCertificateList());
        model.addAttribute("ableServiceList", AbleServiceType.getAbleServiceList());

        return "mypage/petSitterInfoWrite";
    }

    @PostMapping("/create")
    public String createPet(Authentication authentication,
                            PetSitter petSitter,
                            FeeList feeList,
                            WorkingDayAndTime workingDayAndTime) {

        String userName = authentication.getName();
        Member member = memberRepository.findByUserEmail(userName);

        myPageService.savePetSitter(member, petSitter, feeList, workingDayAndTime);

        return "redirect:/mypage/petsitter";
    }

    // 사용자 체크
    @GetMapping("/edit")
    public String editPetSitterForm(Authentication authentication, Model model) {

        String userName = authentication.getName();
        Member member = memberRepository.findByUserEmail(userName);

        PetSitter petSitter = member.getPetSitter();
        FeeList feeList = petSitter.getFeeList();
        WorkingDayAndTime workingDayAndTime = petSitter.getWorkingDayAndTime();

        model.addAttribute("petSitter", petSitter);
        model.addAttribute("feeList", feeList);
        model.addAttribute("workingDayAndTime", workingDayAndTime);
        model.addAttribute("certificateList", CertificateType.getCertificateList());
        model.addAttribute("ableServiceList", AbleServiceType.getAbleServiceList());

        return "mypage/petSitterInfoWrite";
    }

    @PostMapping("/edit")
    public String editPetSitter(Authentication authentication,
                                PetSitter petSitter,
                                FeeList feeList,
                                WorkingDayAndTime workingDayAndTime) {

        String userName = authentication.getName();
        Member member = memberRepository.findByUserEmail(userName);

        myPageService.updatePetSitter(member, petSitter, feeList, workingDayAndTime);

        return "redirect:/mypage/petsitter";
    }
}
