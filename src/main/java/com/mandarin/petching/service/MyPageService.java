package com.mandarin.petching.service;

import com.mandarin.petching.domain.*;
import com.mandarin.petching.dto.PetDto;
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
    public void createPet(Member member, PetDto petDto) {

        Pet pet = Pet.createPet(member, petDto);
        petRepository.save(pet);
    }

    public Pet getPetById(Long petId) {
        return petRepository.findById(petId).get();
    }

    @Transactional
    public void updatePet(Long petId, PetDto petDto) {
        Pet pet = petRepository.findById(petId).get();
        pet.updatePet(petDto);
    }

    @Transactional
    public void savePetSitter(Member member,
                              PetSitter petSitter,
                              FeeList feeList,
                              WorkingDayAndTime workingDayAndTime) {

        petSitter.setMember(member);
        petSitter.setFeeList(feeList);
        petSitter.setWorkingDayAndTime(workingDayAndTime);

        petSitterRepository.save(petSitter);
    }

    @Transactional
    public void updatePetSitter(Member member,
                                PetSitter petSitter,
                                FeeList feeList,
                                WorkingDayAndTime workingDayAndTime) {
        PetSitter findPetSitter = member.getPetSitter();
        petSitterRepository.delete(findPetSitter);

        petSitterRepository.flush();

        savePetSitter(member, petSitter, feeList, workingDayAndTime);
    }
}
