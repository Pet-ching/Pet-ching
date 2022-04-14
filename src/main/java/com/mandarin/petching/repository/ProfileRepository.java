package com.mandarin.petching.repository;

import com.mandarin.petching.domain.PetSitter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class ProfileRepository {

    private final EntityManager em;

    public PetSitter findPetSitter(Long id) {
        return em.find(PetSitter.class, id);
    }
}
