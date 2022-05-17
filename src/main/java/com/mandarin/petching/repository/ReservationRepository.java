package com.mandarin.petching.repository;

import com.mandarin.petching.domain.QFeeList;
import com.mandarin.petching.domain.Reservation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByPetOwnerId(Long petOwnerId);

    List<Reservation> findByPetSitterId(Long petSitterId);


}
