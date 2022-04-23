package com.mandarin.petching.service;

import com.mandarin.petching.domain.Reservation;
import com.mandarin.petching.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public void createReservation(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    public Reservation findReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId).get();
    }

    public List<Reservation> findByPetOwner(Long petOwnerId) {
        return reservationRepository.findByPetOwnerId(petOwnerId);
    }

    public List<Reservation> findByPetSitter(Long petSitterId) {
        return reservationRepository.findByPetSitterId(petSitterId);
    }

    @Transactional
    public void approveReservation(Long reservationId) {
        Reservation reservation = findReservationById(reservationId);
        reservation.setApproval(true);
    }

    @Transactional
    public void refuseReservation(Long reservationId) {
        Reservation reservation = findReservationById(reservationId);
        reservation.setRefusal(true);
    }

    @Transactional
    public void cancelReservation(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }
}
