// code by. hyeok
package com.mandarin.petching.service;

import com.mandarin.petching.domain.PetSitter;
import com.mandarin.petching.repository.InfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class InfoService {

        @Autowired
        private InfoRepository infoRepository;

        public Page<PetSitter> sitterList(Pageable pageable) {
            return infoRepository.findAll(pageable);
        }

        public PetSitter workingArea(Long id) {
            return infoRepository.findById(id).get();
        }

        public Page<PetSitter> sitterSearchList(String searchKeyword, Pageable pageable) {
            return infoRepository.findByWorkingAreaContaining(searchKeyword, pageable);
        }

//        public PetSitter area(PetSitter petSitter) {
//            return infoRepository.findByWorkingArea(petSitter.getWorkingArea());
//        }
}
