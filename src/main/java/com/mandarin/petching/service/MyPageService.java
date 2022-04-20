package com.mandarin.petching.service;

import com.mandarin.petching.domain.Member;
import com.mandarin.petching.domain.Pet;
import com.mandarin.petching.domain.PetOwnerDTO;
import com.mandarin.petching.domain.PetSitter;
import com.mandarin.petching.repository.PetSitterRepository;
import com.mandarin.petching.repository.UserRepository;
import com.mandarin.petching.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final PetSitterRepository petSitterRepository;

    public Member findMemberById(Long memberId) {
        return userRepository.getById(memberId);
    }

    @Transactional
    public void savePet(Long memberId, PetOwnerDTO petOwnerDto) {
        Member member = userRepository.findById(memberId).get();

        Pet pet = Pet.createPetOwner(member, petOwnerDto);
        petRepository.save(pet);
    }

    @Transactional
    public void savePetSitter(Long memberId, PetSitter petSitterDto) {
        Member member = userRepository.findById(memberId).get();

        PetSitter petSitter = PetSitter.createPetSitter(member, petSitterDto);
        petSitterRepository.save(petSitter);
    }
}
