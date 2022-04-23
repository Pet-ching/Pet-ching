package com.mandarin.petching.repository;

import com.mandarin.petching.domain.PetSitter;
import com.mandarin.petching.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByPetOwnerId(Long petOwnerId);

    List<Reservation> findByPetSitterId(Long petSitterId);
}
