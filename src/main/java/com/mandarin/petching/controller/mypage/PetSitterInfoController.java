package com.mandarin.petching.controller.mypage;


import com.mandarin.petching.domain.*;
import com.mandarin.petching.repository.MemberRepository;
import com.mandarin.petching.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/mypage/petsitter")
@RequiredArgsConstructor
public class PetSitterInfoController {

    private final MyPageService myPageService;
    private final MemberRepository memberRepository;

    @GetMapping
    public String petSitterInfoView(Authentication authentication, Model model) {

        Member member = getMember(authentication);

        PetSitter petSitter = member.getPetSitter();

        model.addAttribute("petSitterEmpty", petSitter == null);
        model.addAttribute("petSitter", petSitter);

        return "mypage/petSitterInfo";
    }

    @GetMapping("/create")
    public String createPetForm(Model model) {

        model.addAttribute("petSitter", new PetSitter());
        model.addAttribute("feeList", new FeeList());
        model.addAttribute("workingDayList", WorkingDay.getWorkingList());
        model.addAttribute("certificateList", CertificateType.getCertificateList());
        model.addAttribute("ableServiceList", AbleServiceType.getAbleServiceList());

        return "mypage/petSitterInfoWrite";
    }

    @PostMapping("/create")
    public String createPet(Authentication authentication,
                            @Validated PetSitter petSitter,
                            BindingResult bindingResult,
                            FeeList feeList,
                            List<MultipartFile> files,
                            Model model) throws Exception{

        if (bindingResult.hasErrors()) {

            model.addAttribute("feeList", new FeeList());
            model.addAttribute("workingDayList", WorkingDay.getWorkingList());
            model.addAttribute("certificateList", CertificateType.getCertificateList());
            model.addAttribute("ableServiceList", AbleServiceType.getAbleServiceList());

            return "mypage/petSitterInfoWrite";
        }

        Member member = getMember(authentication);

        myPageService.savePetSitter(member, petSitter, feeList, files);

        return "redirect:/mypage/petsitter";
    }

    @GetMapping("/edit")
    public String editPetSitterForm(Authentication authentication, Model model) {

        Member member = getMember(authentication);

        PetSitter petSitter = member.getPetSitter();
        FeeList feeList = petSitter.getFeeList();

        model.addAttribute("petSitter", petSitter);
        model.addAttribute("feeList", feeList);
        model.addAttribute("workingDayList", WorkingDay.getWorkingList());
        model.addAttribute("certificateList", CertificateType.getCertificateList());
        model.addAttribute("ableServiceList", AbleServiceType.getAbleServiceList());

        return "mypage/petSitterInfoWrite";
    }

    @PostMapping("/edit")
    public String editPetSitter(Authentication authentication,
                                @Validated PetSitter petSitter,
                                BindingResult bindingResult,
                                FeeList feeList,
                                List<MultipartFile> files,
                                Model model) throws Exception{

        if (bindingResult.hasErrors()) {

            model.addAttribute("feeList", new FeeList());
            model.addAttribute("workingDayList", WorkingDay.getWorkingList());
            model.addAttribute("certificateList", CertificateType.getCertificateList());
            model.addAttribute("ableServiceList", AbleServiceType.getAbleServiceList());

            return "mypage/petSitterInfoWrite";
        }

        Member member = getMember(authentication);

        myPageService.updatePetSitter(member, petSitter, feeList, files);

        return "redirect:/mypage/petsitter";
    }

    private Member getMember(Authentication authentication) {
        String userName = authentication.getName();
        return memberRepository.findByUserEmail(userName);
    }
}
