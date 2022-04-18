package com.mandarin.petching.repository;


import com.mandarin.petching.domain.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import javax.persistence.EntityManager;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
