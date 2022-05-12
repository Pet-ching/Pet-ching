package com.mandarin.petching.service;

import com.mandarin.petching.dto.CountByPriceDTO;
import com.mandarin.petching.dto.PriceDTO;
import com.mandarin.petching.repository.FeeListRepository;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceInformationService {
    private final FeeListRepository feeListRepository;

    public List<PriceDTO> getPetSitterPrice()
    {
        List<PriceDTO> result = new ArrayList<PriceDTO>();

        //소-중-대(견) -> 고양이 -> 기타 동물 순
        result.add(feeListRepository.findSmallDogPriceStatics());
        result.add(feeListRepository.findMiddleDogPriceStatics());
        result.add(feeListRepository.findLargeDogPriceStatics());
        result.add(feeListRepository.findCatPriceStatics());
        result.add(feeListRepository.findEtcPriceStatics());

        return result;
    }

    public List<List<CountByPriceDTO>> getPetSitterCountByPrice()
    {
        List<List<CountByPriceDTO>> result = new ArrayList<>();

        //소-중-대(견) -> 고양이 -> 기타 동물 순
        result.add(feeListRepository.findSmallDogCountByPrince());
        result.add(feeListRepository.findMiddleDogCountByPrince());
        result.add(feeListRepository.findLargeDogCountByPrince());
        result.add(feeListRepository.findCatCountByPrince());
        result.add(feeListRepository.findEtcCountByPrince());

        return result;
    }

}
