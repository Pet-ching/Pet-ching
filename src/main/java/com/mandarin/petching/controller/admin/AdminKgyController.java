package com.mandarin.petching.controller.admin;

import com.mandarin.petching.domain.Pet;
import com.mandarin.petching.domain.Role;
import com.mandarin.petching.dto.*;
import com.mandarin.petching.service.AdminKgyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminKgyController {
    private final AdminKgyService adminKgyService;

    //선택화면
    @GetMapping({"", "/"})
    public String choice() {

        return "/qna/choice";
    }

    //가격 통계 정보
    @GetMapping("/price")
    public String price(Model model)
    {
        //소-중-대(견) -> 고양이 -> 기타 동물 순
        //총 수, 최댓값, 최솟값, 평균
        MathDTO smallDog = adminKgyService.getPetSitterPrice().get(0);
        MathDTO middleDog = adminKgyService.getPetSitterPrice().get(1);
        MathDTO largeDog = adminKgyService.getPetSitterPrice().get(2);
        MathDTO cat = adminKgyService.getPetSitterPrice().get(3);
        MathDTO etc = adminKgyService.getPetSitterPrice().get(4);

        model.addAttribute("smallDog", smallDog);
        model.addAttribute("middleDog", middleDog);
        model.addAttribute("largeDog", largeDog);
        model.addAttribute("cat", cat);
        model.addAttribute("etc", etc);

        //가격 별로 인원수
        //소-중-대(견) -> 고양이 -> 기타 동물 순
        List<CountByNumDTO> smallDogCountByPrice = adminKgyService.getPetSitterCountByPrice().get(0);
        List<CountByNumDTO> middleDogCountByPrice = adminKgyService.getPetSitterCountByPrice().get(1);
        List<CountByNumDTO> largeDogCountByPrice = adminKgyService.getPetSitterCountByPrice().get(2);
        List<CountByNumDTO> catCountByPrice = adminKgyService.getPetSitterCountByPrice().get(3);
        List<CountByNumDTO> etcCountByPrice = adminKgyService.getPetSitterCountByPrice().get(4);

        model.addAttribute("smallDogCountByPrice",smallDogCountByPrice);
        model.addAttribute("middleDogCountByPrice",middleDogCountByPrice);
        model.addAttribute("largeDogCountByPrice",largeDogCountByPrice);
        model.addAttribute("catCountByPrice",catCountByPrice);
        model.addAttribute("etcCountByPrice",etcCountByPrice);

        return "/admin/petsitterchart";
    }

    //매칭지역 정보
    @GetMapping("/area")
    public String matchingArea(Model model)
    {
        List<AreaDTO> list = adminKgyService.getMatchingArea();
        model.addAttribute("area", list);

        return "/admin/petsitterchart";
    }

    @GetMapping("/certificate")
    public String certificateInfo(Model model)
    {
        List<CertificateDTO> list = adminKgyService.getCountByCertificate();

        model.addAttribute("certificateName", list.get(0).getName());
        model.addAttribute("certificate",list);

        return "/admin/petsitterchart";
    }

    @GetMapping("/owner")
    public String petOwnerInfo(Model model)
    {
        List<PetOwnerDTO> age = adminKgyService.getPetOwnerAgeList();
        List<PetOwnerDTO> residence = adminKgyService.getPetOwnerResidenceList();
        List<CountByStringDTO> count = adminKgyService.getPetCountByPetOwnerList();
        model.addAttribute("age", age);
        model.addAttribute("residence", residence);
        model.addAttribute("count", count);

        return "/admin/petownerchart";
    }

    @GetMapping("/pet")
    public String petInfo(Model model) {

        List<PetDTO> pet = adminKgyService.getAllPetList();
        model.addAttribute("pet", pet);
        return "/admin/petchart";
    }

    @GetMapping("/deny")
    public String deny(){

        return "/testAdmin/deny";
    }

}
