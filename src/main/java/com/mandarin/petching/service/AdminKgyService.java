package com.mandarin.petching.service;

import com.mandarin.petching.domain.Member;
import com.mandarin.petching.domain.Reservation;
import com.mandarin.petching.dto.*;
import com.mandarin.petching.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AdminKgyService {

    private final ReservationRepository reservationRepository;
    private final QDReservationRepository qdReservationRepository;
    private final MemberRepository memberRepository;
    private final QDCertificateRepository certificateRepository;
    private final QDMemberRepository qdMemberRepository;
    private final QDPetRepository petRepository;


    private final QDFeeListRepository feeListRepository;

    //가격 정보
    public List<MathDTO> getPetSitterPrice()
    {
        List<MathDTO> result = new ArrayList<MathDTO>();

        //소-중-대(견) -> 고양이 -> 기타 동물 순
        result.add(feeListRepository.findSmallDogPriceStatics());
        result.add(feeListRepository.findMiddleDogPriceStatics());
        result.add(feeListRepository.findLargeDogPriceStatics());
        result.add(feeListRepository.findCatPriceStatics());
        result.add(feeListRepository.findEtcPriceStatics());

        return result;
    }

    public List<List<CountByNumDTO>> getPetSitterCountByPrice()
    {
        List<List<CountByNumDTO>> result = new ArrayList<>();

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

    //보호자 정보

    //보호자 나이
    public List<PetOwnerDTO> getPetOwnerAgeList()
    {
        List<PetOwnerDTO> result = qdMemberRepository.findPetOwnerAgeStatistics();
        for(int i=0;i<result.size();i++)
        {
            //오늘 날짜 구하고 int로 만들기
            LocalDate now = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
            String thisYearStr = now.format(formatter);
            int thisYear = Integer.parseInt(thisYearStr);


            //나이 구하기
            String[] birth =  result.get(i).getUser_bth().split("-");
            int memberBirthYear = Integer.parseInt(birth[0]);
            int age = thisYear - memberBirthYear + 1;


            result.get(i).setAge(age);
        }

        return result;
    }

    //보호자 거주지
    public List<PetOwnerDTO> getPetOwnerResidenceList()
    {
        return qdMemberRepository.findPetOwnerResidence();
    }

    //보호자 가정당 반려동물 수 정보
    public  List<CountByStringDTO> getPetCountByPetOwnerList()
    {
        return qdMemberRepository.findPetCountByPetOwner();
    }

    //반려동물
    public List<PetDTO> getAllPetList()
    {
        return petRepository.findPetInfo();
    }


    //예약정보 페이지
    public Page<ReservationDTO> getAllReservationPage(Pageable pageable)
    {
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize());
        return  qdReservationRepository.findReservationPage(pageable);
    }

    public List<CountByNumDTO> getPetCountByAgeList()
    {
        List<CountByNumDTO> list = petRepository.findCountByAge();
        return list;
    }

    public List<CountByBooleanDTO> getPetCountByNeutralizationList()
    {
        List<CountByBooleanDTO> list = petRepository.findCountByNeutralization();
        return list;
    }

    public List<CountByPetTypeDTO> getPetCountByPetTypeList()
    {
        List<CountByPetTypeDTO> list = petRepository.findCountByPetType();
        return list;
    }

}
