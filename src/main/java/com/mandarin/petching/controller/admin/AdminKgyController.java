package com.mandarin.petching.controller.admin;

import com.mandarin.petching.dto.CertificateDTO;
import com.mandarin.petching.dto.CountByPriceDTO;
import com.mandarin.petching.dto.PriceDTO;
import com.mandarin.petching.service.AdminKgyService;
import com.mandarin.petching.dto.AreaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
        PriceDTO smallDog = adminKgyService.getPetSitterPrice().get(0);
        PriceDTO middleDog = adminKgyService.getPetSitterPrice().get(1);
        PriceDTO largeDog = adminKgyService.getPetSitterPrice().get(2);
        PriceDTO cat = adminKgyService.getPetSitterPrice().get(3);
        PriceDTO etc = adminKgyService.getPetSitterPrice().get(4);

        model.addAttribute("smallDog", smallDog);
        model.addAttribute("middleDog", middleDog);
        model.addAttribute("largeDog", largeDog);
        model.addAttribute("cat", cat);
        model.addAttribute("etc", etc);

        //가격 별로 인원수
        //소-중-대(견) -> 고양이 -> 기타 동물 순
        List<CountByPriceDTO> smallDogCountByPrice = adminKgyService.getPetSitterCountByPrice().get(0);
        List<CountByPriceDTO> middleDogCountByPrice = adminKgyService.getPetSitterCountByPrice().get(1);
        List<CountByPriceDTO> largeDogCountByPrice = adminKgyService.getPetSitterCountByPrice().get(2);
        List<CountByPriceDTO> catCountByPrice = adminKgyService.getPetSitterCountByPrice().get(3);
        List<CountByPriceDTO> etcCountByPrice = adminKgyService.getPetSitterCountByPrice().get(4);

        model.addAttribute("smallDogCountByPrice",smallDogCountByPrice);
        model.addAttribute("middleDogCountByPrice",middleDogCountByPrice);
        model.addAttribute("largeDogCountByPrice",largeDogCountByPrice);
        model.addAttribute("catCountByPrice",catCountByPrice);
        model.addAttribute("etcCountByPrice",etcCountByPrice);

        return "/testAdmin/price";
    }

    //매칭지역 정보
    @GetMapping("/area")
    public String matchingArea(Model model)
    {
        List<AreaDTO> list = adminKgyService.getMatchingArea();
        model.addAttribute("area", list);

        return "/testAdmin/area";
    }

    @GetMapping("/certificate")
    public String certificate(Model model)
    {
        List<CertificateDTO> list = adminKgyService.getCountByCertificate();
        model.addAttribute("certificate",list);

        return "/testAdmin/certificate";
    }
}
