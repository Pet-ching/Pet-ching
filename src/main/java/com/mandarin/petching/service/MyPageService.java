package com.mandarin.petching.service;

import com.mandarin.petching.domain.*;
import com.mandarin.petching.dto.PetDto;
import com.mandarin.petching.repository.PetSitterRepository;
import com.mandarin.petching.repository.RoomRepository;
import com.mandarin.petching.repository.UserRepository;
import com.mandarin.petching.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final PetSitterRepository petSitterRepository;
    private final RoomRepository roomRepository;

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
    public void updatePet(Long petId, Pet pet) {

        Pet findPet = petRepository.findById(petId).get();
        findPet.setPetName(pet.getPetName());
        findPet.setPetGender(pet.getPetGender());
        findPet.setPetType(pet.getPetType());
        findPet.setPetAge(pet.getPetAge());
        findPet.setWeight(pet.getWeight());
        findPet.setNeutralization(pet.isNeutralization());
        findPet.setHospitalName(pet.getHospitalName());
        findPet.setHospitalTel(pet.getHospitalTel());
        findPet.setHospitalAdr(pet.getHospitalAdr());
        findPet.setNote(pet.getNote());
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

        findPetSitter.setCertificate(petSitter.getCertificate());
        findPetSitter.setAbleService(petSitter.getAbleService());
        findPetSitter.setWorkingArea(petSitter.getWorkingArea());
        findPetSitter.setSelfIntroduction(petSitter.getSelfIntroduction());
        findPetSitter.setTitle(petSitter.getTitle());
        findPetSitter.setFeeList(feeList);
        findPetSitter.setWorkingDayAndTime(workingDayAndTime);
    }

    public List<ChatRoom> getChatList(Long memberId) {

        List<ChatRoom> chatRoomList = new ArrayList<>();
        List<ChatRoom> buyerChatRooms = roomRepository.findByPetOwnerId(memberId);
        List<ChatRoom> sellerChatRooms = roomRepository.findByPetSitterId(memberId);

        chatRoomList.addAll(buyerChatRooms);
        chatRoomList.addAll(sellerChatRooms);

        return chatRoomList;
    }
}
