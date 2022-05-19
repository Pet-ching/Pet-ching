package com.mandarin.petching.repository;


import com.mandarin.petching.domain.QMember;
import com.mandarin.petching.domain.QPet;
import com.mandarin.petching.dto.CountByStringDTO;
import com.mandarin.petching.dto.PetOwnerDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class QDMemberRepository {
    private final JPAQueryFactory queryFactory;
    private QMember member = QMember.member;
    private QPet pet = QPet.pet;

    //보호자 연령대 정보
    public List<PetOwnerDTO> findPetOwnerAgeStatistics()
    {
//        select pet_name, pet_type, user_name, user_email, user_bth from pet inner join member on pet.member_id = member.member_id;
        List<PetOwnerDTO> result = queryFactory.select(Projections.bean(PetOwnerDTO.class,
                pet.petName.as("pet_name"),pet.petType.as("pet_type"),member.userName.as("user_name"),member.userEmail.as("user_email"),member.userBth.as("user_bth")))
                .from(member)
                .join(pet)
                .on(member.id.eq(pet.member.id))
                .fetch();

        return result;
    }

    //보호자 거주 지역 정보
    public List<PetOwnerDTO> findPetOwnerResidence()
    {
        List<PetOwnerDTO> result = queryFactory.select(Projections.bean(PetOwnerDTO.class,
                        pet.petName.as("pet_name"),pet.petType.as("pet_type"),member.userName.as("user_name"),member.userEmail.as("user_email"),member.address.as("user_address")))
                .from(member)
                .join(pet)
                .on(member.id.eq(pet.member.id))
                .fetch();

        return result;
    }

    //보호자 가정당 반려동물 수 정보
    public List<CountByStringDTO> findPetCountByPetOwner()
    {
        //select user_name, user_email, count(pet.pet_id) from pet inner join member on pet.member_id = member.member_id group by member.member_id;
        List<CountByStringDTO> result = queryFactory.select(Projections.bean(CountByStringDTO.class, member.userName.as("name"),member.userEmail.as("email"), pet.id.count().as("count")))
                .from(member)
                .innerJoin(pet)
                .on(member.id.eq(pet.member.id))
                .groupBy(member.id)
                .fetch();

        return result;
    }

}
