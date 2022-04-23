package com.mandarin.petching.service;

import com.mandarin.petching.domain.*;
import com.mandarin.petching.dto.PetDto;
import com.mandarin.petching.repository.PetSitterRepository;
import com.mandarin.petching.repository.UserRepository;
import com.mandarin.petching.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public boolean createPet(Member member, Pet pet) {

        List<Pet> petList = member.getPetList();
        for (Pet p : petList) {
            if (p.getPetName() == pet.getPetName()) {
                return false;
            }
        }

        pet.setMember(member);
        petRepository.save(pet);
        return true;
    }

    public Pet getPetById(Long petId) {
        return petRepository.findById(petId).get();
    }

    @Transactional
    public void updatePet(Long petId, Pet pet, Member member) {

        Pet findPet = petRepository.findById(petId).get();
        petRepository.delete(findPet);

        petRepository.flush();

        createPet(member, pet);
    }

    @Transactional
    public void deletePet(Long petId) {
        petRepository.deleteById(petId);
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
