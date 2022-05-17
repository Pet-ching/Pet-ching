package com.mandarin.petching.repository;


import com.mandarin.petching.domain.FeeList;
import com.mandarin.petching.domain.QFeeList;
import com.mandarin.petching.dto.CountByPriceDTO;
import com.mandarin.petching.dto.PriceDTO;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class QDFeeListRepository {
    private final JPAQueryFactory queryFactory;
    private QFeeList feeList = QFeeList.feeList;


    //고양이 가격 통계
    public PriceDTO findCatPriceStatics()
    {
        Tuple priceStaticsTuple  = getPriceStaticsTuple(feeList.catFee);
        List<CountByPriceDTO> countByPriceList = getCountByPrice(feeList.catFee);
        PriceDTO price = getPriceStatics(priceStaticsTuple, feeList.catFee);

        return price;
    }


    public PriceDTO findSmallDogPriceStatics()
    {
        Tuple priceStaticsTuple  = getPriceStaticsTuple(feeList.smallDogFee);
        PriceDTO price = getPriceStatics(priceStaticsTuple, feeList.smallDogFee);

        return price;
    }
    public PriceDTO findMiddleDogPriceStatics()
    {
        Tuple priceStaticsTuple  = getPriceStaticsTuple(feeList.middleDogFee);
        PriceDTO price = getPriceStatics(priceStaticsTuple, feeList.middleDogFee);

        return price;
    }
    public PriceDTO findLargeDogPriceStatics()
    {
        Tuple priceStaticsTuple  = getPriceStaticsTuple(feeList.largeDogFee);
        PriceDTO price = getPriceStatics(priceStaticsTuple, feeList.largeDogFee);

        return price;
    }
    public PriceDTO findEtcPriceStatics()
    {
        Tuple priceStaticsTuple = getPriceStaticsTuple(feeList.etcFee);
        PriceDTO price = getPriceStatics(priceStaticsTuple, feeList.etcFee);

        return price;
    }

    //가격 별로 나눔
    public List<CountByPriceDTO> findSmallDogCountByPrince()
    {
        return getCountByPrice(feeList.smallDogFee);
    }
    public List<CountByPriceDTO> findMiddleDogCountByPrince()
    {
        return getCountByPrice(feeList.middleDogFee);
    }
    public List<CountByPriceDTO> findLargeDogCountByPrince()
    {
        return getCountByPrice(feeList.largeDogFee);
    }
    public List<CountByPriceDTO> findCatCountByPrince()
    {
        return getCountByPrice(feeList.catFee);
    }
    public List<CountByPriceDTO> findEtcCountByPrince()
    {
        return getCountByPrice(feeList.etcFee);
    }



    //최댓값, 최솟값, 총 수, 평균값
    public Tuple getPriceStaticsTuple(NumberPath pet)
    {
        Tuple result = queryFactory.select(pet.count(), pet.min(), pet.max(), pet.avg())
                .from(feeList)
                .fetchOne();

        return result;
    }

    //최댓값, 최솟값, 총 수, 평균값
    public PriceDTO getPriceStatics(Tuple tuple, NumberPath<Integer> price)
    {
        PriceDTO priceDTO = new PriceDTO();
        priceDTO.setTotal(tuple.get(price.count()));
        priceDTO.setMin(tuple.get(price.min()));
        priceDTO.setMax(tuple.get(price.max()));
        priceDTO.setAvg(tuple.get(price.avg()));

        return priceDTO;
    }


    //가격별로 수 세기
    public List<CountByPriceDTO> getCountByPrice(NumberPath pet)
    {
        List<CountByPriceDTO> result = queryFactory.select(Projections.fields(CountByPriceDTO.class, pet.as("price"), pet.count().as("count")))
                .from(feeList)
                .groupBy(pet)
                .fetch();

        return result;
    }


}
