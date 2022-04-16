package com.mandarin.petching.service;

import com.mandarin.petching.domain.GenderType;
import com.mandarin.petching.domain.PetSitter;
import com.mandarin.petching.repository.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
//@Rollback(value = false)
class ProfileServiceTest {

    @Autowired ProfileService profileService;
    @Autowired EntityManager em;

    @Test
    public void findProfileTest() throws Exception {

        PetSitter petSitter = createPetSitter();
        Long petSitterId = savePetSitter(petSitter);

        PetSitter findPetSitter = profileService.findPetSitter(petSitterId);

        assertEquals(petSitter, findPetSitter);
    }

    private Long savePetSitter(PetSitter petSitter) {
        if (petSitter.getId() == null) {
            em.persist(petSitter);
        } else {
            em.merge(petSitter);
        }

        return petSitter.getId();
    }

    private PetSitter createPetSitter() {
        PetSitter petSitter = new PetSitter();

        petSitter.setAbleService("play");
        petSitter.setCertificate("expert");
        petSitter.setFee("소형견 10000,");
        petSitter.setSelfIntroduction("안녕하세요");
        petSitter.setWorkingArea("강남구");
        petSitter.setWorkingTime("10:00~18:00");
        petSitter.setChatUrl("C:\\woking\\drive");
        petSitter.setImageUrl("C:\\working\\drive");
        petSitter.setUserBth(LocalDate.now());
        petSitter.setUserEmail("yeon@mandarin");
        petSitter.setUserGender(GenderType.FEMALE);
        petSitter.setUserId("yeon");
        petSitter.setUserName("hey");
        petSitter.setUserPwd("1234");
        petSitter.setUserTel("010-1111-1111");
        return petSitter;
    }
}