package com.mandarin.petching.service;

import com.mandarin.petching.domain.PetSitter;
import com.mandarin.petching.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    public PetSitter findPetSitter(Long id) {
        return profileRepository.findPetSitter(id);
    }
}
