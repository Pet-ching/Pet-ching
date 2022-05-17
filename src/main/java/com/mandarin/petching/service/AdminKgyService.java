package com.mandarin.petching.service;

import com.mandarin.petching.domain.Member;
import com.mandarin.petching.domain.Reservation;
import com.mandarin.petching.dto.CertificateDTO;
import com.mandarin.petching.dto.CountByPriceDTO;
import com.mandarin.petching.dto.PriceDTO;
import com.mandarin.petching.dto.AreaDTO;
import com.mandarin.petching.repository.MemberRepository;
import com.mandarin.petching.repository.QDCertificateRepository;
import com.mandarin.petching.repository.QDFeeListRepository;
import com.mandarin.petching.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AdminKgyService {

    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final QDCertificateRepository certificateRepository;

    private final QDFeeListRepository feeListRepository;

    //가격 정보
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

    //지역 정보
    public List<AreaDTO> getMatchingArea()
    {
        List<Reservation> reservationList = reservationRepository.findAll();
        Hashtable<String, Long> addressTable = new Hashtable<>();
        List<AreaDTO> result = new ArrayList<>();

        for(int i=0; i<reservationList.size(); i++) {
            Long id = reservationList.get(i).getPetOwnerId();
            Member member = memberRepository.getById(id);

            //주소 행정 구역 구까지만 저장
            String[] strTemp = member.getAddress().split(" ");
            String address = strTemp[0] + " " + strTemp[1];

            //있다면 주소 카운트
            if(addressTable.containsKey(address))
            {
                Long count = addressTable.get(address) + 1;
                addressTable.put(address, count);
            }
            else//주솟값 없다면 -> 생성
            {
                addressTable.put(address, 1L);
            }
        }

        // Hashtable -> DTO
        Iterator<Map.Entry<String, Long>> iterator = addressTable.entrySet().iterator();
        while(iterator.hasNext())
        {
            Map.Entry<String, Long> mapArea =  iterator.next();

            AreaDTO area = new AreaDTO();
            area.setAddress(mapArea.getKey());
            area.setCount(mapArea.getValue());

            result.add(area);
        }


        return result;
    }

    //자격증 정보
    public List<CertificateDTO> getCountByCertificate()
    {

        return certificateRepository.findCountByCertificate();
    }
}
