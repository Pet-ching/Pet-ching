package com.mandarin.petching.repository;

import com.mandarin.petching.domain.QMember;
import com.mandarin.petching.domain.QPet;
import com.mandarin.petching.domain.QPetSitter;
import com.mandarin.petching.domain.QReservation;
import com.mandarin.petching.dto.ReservationDTO;
import com.querydsl.core.Query;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QDReservationRepository {
    private final JPAQueryFactory jpaQueryFactory;
    QReservation reservation = QReservation.reservation;
    QMember petOwner = QMember.member;
    QPetSitter petSitter = QPetSitter.petSitter;
    QPet pet = QPet.pet;

    public Page<ReservationDTO> findReservationPage(Pageable pageable)
    {
        List<ReservationDTO> list = jpaQueryFactory.select(Projections.bean(ReservationDTO.class,
                reservation.approval.as("approval"), reservation.fee.as("fee"), reservation.startDate.as("startDate"),reservation.endDate.as("endDate"),
                petOwner.id.as("petOwnerId"), petOwner.userName.as("petOwnerName"),
                petSitter.id.as("petSitterId"), petSitter.member.userName.as("petSitterName"),
                pet.id.as("petId"), pet.petName.as("petName")
                ))
                .from(reservation)
                .leftJoin(petOwner)
                .on(reservation.petOwnerId.eq(petOwner.id))
                .leftJoin(petSitter)
                .on(reservation.petSitterId.eq(petSitter.id))
                .leftJoin(pet)
                .on(reservation.pet.id.eq(pet.id))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        	// List 의 size 메소드로 카운트를 넘겨준다.
        Page result = new PageImpl(list, pageable, list.size());


        return result;
    }



}
