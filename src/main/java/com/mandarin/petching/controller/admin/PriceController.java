package com.mandarin.petching.controller.admin;

import com.mandarin.petching.domain.QFeeList;
import com.mandarin.petching.dto.CountByPriceDTO;
import com.mandarin.petching.dto.PriceDTO;
import com.mandarin.petching.repository.FeeListRepository;
import com.mandarin.petching.service.PriceInformationService;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class PriceController {
    private final PriceInformationService priceInformationService;

    //선택화면
    @GetMapping({"", "/"})
    public String choice() {

        return "/qna/choice";
    }

    @GetMapping("/price")
    public String price(Model model)
    {
        //소-중-대(견) -> 고양이 -> 기타 동물 순
        //총 수, 최댓값, 최솟값, 평균
        PriceDTO smallDog = priceInformationService.getPetSitterPrice().get(0);
        PriceDTO middleDog = priceInformationService.getPetSitterPrice().get(1);
        PriceDTO largeDog = priceInformationService.getPetSitterPrice().get(2);
        PriceDTO cat = priceInformationService.getPetSitterPrice().get(3);
        PriceDTO etc = priceInformationService.getPetSitterPrice().get(4);

        model.addAttribute("smallDog", smallDog);
        model.addAttribute("middleDog", middleDog);
        model.addAttribute("largeDog", largeDog);
        model.addAttribute("cat", cat);
        model.addAttribute("etc", etc);

        //가격 별로 인원수
        //소-중-대(견) -> 고양이 -> 기타 동물 순
        List<CountByPriceDTO> smallDogCountByPrice = priceInformationService.getPetSitterCountByPrice().get(0);
        List<CountByPriceDTO> middleDogCountByPrice = priceInformationService.getPetSitterCountByPrice().get(1);
        List<CountByPriceDTO> largeDogCountByPrice = priceInformationService.getPetSitterCountByPrice().get(2);
        List<CountByPriceDTO> catCountByPrice = priceInformationService.getPetSitterCountByPrice().get(3);
        List<CountByPriceDTO> etcCountByPrice = priceInformationService.getPetSitterCountByPrice().get(4);

        model.addAttribute("smallDogCountByPrice",smallDogCountByPrice);
        model.addAttribute("middleDogCountByPrice",middleDogCountByPrice);
        model.addAttribute("largeDogCountByPrice",largeDogCountByPrice);
        model.addAttribute("catCountByPrice",catCountByPrice);
        model.addAttribute("etcCountByPrice",etcCountByPrice);

        return "/testAdmin/price";
    }

}
