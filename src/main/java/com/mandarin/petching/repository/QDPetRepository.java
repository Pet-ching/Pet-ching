package com.mandarin.petching.repository;

import com.mandarin.petching.domain.QMember;
import com.mandarin.petching.domain.QPet;
import com.mandarin.petching.dto.PetDTO;
import com.mandarin.petching.dto.PetOwnerDTO;
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
}
