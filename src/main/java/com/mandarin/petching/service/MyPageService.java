package com.mandarin.petching.service;

import com.mandarin.petching.domain.Member;
import com.mandarin.petching.domain.PetOwner;
import com.mandarin.petching.domain.PetOwnerDTO;
import com.mandarin.petching.repository.UserRepository;
import com.mandarin.petching.repository.PetOwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;
    private final PetOwnerRepository petOwnerRepository;

    public Member findMemberById(Long memberId) {
        return userRepository.getById(memberId);
    }

    @Transactional
    public void savePetOwner(Long memberId, PetOwnerDTO petOwnerDto) {
        Member member = userRepository.findById(memberId).get();

        PetOwner petOwner = PetOwner.createPetOwner(member, petOwnerDto);
        petOwnerRepository.save(petOwner);
    }
}
