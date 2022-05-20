package com.mandarin.petching.repository;

import com.mandarin.petching.domain.QMember;
import com.mandarin.petching.domain.QPet;
import com.mandarin.petching.dto.*;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QDPetRepository {
    private final JPAQueryFactory queryFactory;
    QPet pet = QPet.pet;
    QMember member = QMember.member;

    public List<PetDTO> findPetInfo()
    {
//        select user_name, user_email, pet.pet_name, pet.pet_age, pet.neutralization from pet inner join member on pet.member_id = member.member_id;
        List<PetDTO> result = queryFactory.select(Projections.bean(PetDTO.class,
                member.userName.as("user_name"), member.userEmail.as("user_email"), pet.petName.as("pet_name"), pet.petAge.as("pet_age"), pet.neutralization.as("neutralization"), pet.petType.as("pet_type")))
                .from(pet)
                .innerJoin(member)
                .on(member.id.eq(pet.member.id))
                .fetch();

        return result;
    }

    public List<CountByNumDTO> findCountByAge(){
        List<CountByNumDTO> result = queryFactory.select(Projections.bean(CountByNumDTO.class, pet.petAge.as("num") ,pet.petAge.count().as("count")))
                .from(pet)
                .groupBy(pet.petAge)
                .fetch();

        return result;
    }

    public List<CountByBooleanDTO> findCountByNeutralization()
    {
        List<CountByBooleanDTO> result = queryFactory.select(Projections.bean(CountByBooleanDTO.class, pet.neutralization.as("bool"), pet.neutralization.count().as("count")))
                .from(pet)
                .groupBy(pet.neutralization)
                .fetch();

        return result;


    }
    public List<CountByPetTypeDTO> findCountByPetType()
    {
        List<CountByPetTypeDTO> result = queryFactory.select(Projections.bean(CountByPetTypeDTO.class, pet.petType.as("petType"), pet.petType.count().as("count")))
                .from(pet)
                .groupBy(pet.petType)
                .fetch();

        return result;

    }
}
