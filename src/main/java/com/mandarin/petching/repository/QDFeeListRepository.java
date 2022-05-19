package com.mandarin.petching.repository;


import com.mandarin.petching.domain.QFeeList;
import com.mandarin.petching.dto.CountByNumDTO;
import com.mandarin.petching.dto.MathDTO;
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
    public MathDTO findCatPriceStatics()
    {
        Tuple priceStaticsTuple  = getPriceStaticsTuple(feeList.catFee);
        List<CountByNumDTO> countByPriceList = getCountByPrice(feeList.catFee);
        MathDTO price = getPriceStatics(priceStaticsTuple, feeList.catFee);

        return price;
    }


    public MathDTO findSmallDogPriceStatics()
    {
        Tuple priceStaticsTuple  = getPriceStaticsTuple(feeList.smallDogFee);
        MathDTO price = getPriceStatics(priceStaticsTuple, feeList.smallDogFee);

        return price;
    }
    public MathDTO findMiddleDogPriceStatics()
    {
        Tuple priceStaticsTuple  = getPriceStaticsTuple(feeList.middleDogFee);
        MathDTO price = getPriceStatics(priceStaticsTuple, feeList.middleDogFee);

        return price;
    }
    public MathDTO findLargeDogPriceStatics()
    {
        Tuple priceStaticsTuple  = getPriceStaticsTuple(feeList.largeDogFee);
        MathDTO price = getPriceStatics(priceStaticsTuple, feeList.largeDogFee);

        return price;
    }
    public MathDTO findEtcPriceStatics()
    {
        Tuple priceStaticsTuple = getPriceStaticsTuple(feeList.etcFee);
        MathDTO price = getPriceStatics(priceStaticsTuple, feeList.etcFee);

        return price;
    }

    //가격 별로 나눔
    public List<CountByNumDTO> findSmallDogCountByPrince()
    {
        return getCountByPrice(feeList.smallDogFee);
    }
    public List<CountByNumDTO> findMiddleDogCountByPrince()
    {
        return getCountByPrice(feeList.middleDogFee);
    }
    public List<CountByNumDTO> findLargeDogCountByPrince()
    {
        return getCountByPrice(feeList.largeDogFee);
    }
    public List<CountByNumDTO> findCatCountByPrince()
    {
        return getCountByPrice(feeList.catFee);
    }
    public List<CountByNumDTO> findEtcCountByPrince()
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
    public MathDTO getPriceStatics(Tuple tuple, NumberPath<Integer> price)
    {
        MathDTO mathDTO = new MathDTO();
        mathDTO.setTotal(tuple.get(price.count()));
        mathDTO.setMin(tuple.get(price.min()));
        mathDTO.setMax(tuple.get(price.max()));
        mathDTO.setAvg(tuple.get(price.avg()));

        return mathDTO;
    }


    //가격별로 수 세기
    public List<CountByNumDTO> getCountByPrice(NumberPath pet)
    {
        List<CountByNumDTO> result = queryFactory.select(Projections.fields(CountByNumDTO.class, pet.as("num"), pet.count().as("count")))
                .from(feeList)
                .groupBy(pet)
                .fetch();

        return result;
    }


}
