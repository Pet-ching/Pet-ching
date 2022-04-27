package com.mandarin.petching.service;

import com.mandarin.petching.domain.*;
import com.mandarin.petching.repository.*;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyPageService {

    private final MemberRepository memberRepository;
    private final PetRepository petRepository;
    private final PetSitterRepository petSitterRepository;
    private final RoomRepository roomRepository;

    public Member findMemberById(Long memberId) {
        return memberRepository.getById(memberId);
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
                              List<MultipartFile> files) throws Exception{

        petSitter.setMember(member);
        petSitter.setFeeList(feeList);

        for(MultipartFile file : files) {
            String fileName = getFileName(file);
            petSitter.getImgPaths().add("/images/memberImages/" + fileName);
        }

        petSitterRepository.save(petSitter);
    }

    @Transactional
    public void updatePetSitter(Member member,
                                PetSitter petSitter,
                                FeeList feeList,
                                List<MultipartFile> files) throws Exception{

        PetSitter findPetSitter = member.getPetSitter();

        findPetSitter.setCertificate(petSitter.getCertificate());
        findPetSitter.setAbleService(petSitter.getAbleService());
        findPetSitter.setWorkingArea(petSitter.getWorkingArea());
        findPetSitter.setSelfIntroduction(petSitter.getSelfIntroduction());
        findPetSitter.setTitle(petSitter.getTitle());
        findPetSitter.setWorkingDay(petSitter.getWorkingDay());
        findPetSitter.setFeeList(feeList);

        for(MultipartFile file : files) {
            String fileName = getFileName(file);
            findPetSitter.getImgPaths().add("/images/memberImages/" + fileName);
        }
    }

    public List<ChatRoom> getChatList(Long memberId) {

        List<ChatRoom> chatRoomList = new ArrayList<>();
        List<ChatRoom> buyerChatRooms = roomRepository.findByPetOwnerId(memberId);
        List<ChatRoom> sellerChatRooms = roomRepository.findByPetSitterId(memberId);

        chatRoomList.addAll(buyerChatRooms);
        chatRoomList.addAll(sellerChatRooms);

        return chatRoomList;
    }

    @NotNull
    private String getFileName(MultipartFile file) throws IOException {

        String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images\\memberImages";
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();
        File saveFile = new File(filePath, fileName);
        file.transferTo(saveFile);

        return fileName;
    }
}
