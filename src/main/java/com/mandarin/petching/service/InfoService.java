// code by. hyeok
package com.mandarin.petching.service;

import com.mandarin.petching.domain.Member;
import com.mandarin.petching.domain.PetSitter;
import com.mandarin.petching.domain.Review;
import com.mandarin.petching.repository.InfoRepository;
import com.mandarin.petching.repository.MemberRepository;
import com.mandarin.petching.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class InfoService {

    @Autowired
    private InfoRepository infoRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public InfoService() {
    }

    public Page<PetSitter> sitterList(Pageable pageable) {
        return infoRepository.findAll(pageable);
    }

    public PetSitter findById(Long id) {
        return infoRepository.findById(id).get();
    }

    public Page<PetSitter> sitterSearchList(String searchKeyword, Pageable pageable) {
        return infoRepository.findByWorkingAreaContaining(searchKeyword, pageable);
    }


    public Page<PetSitter> findByAbleService(String ableService, Pageable pageable) {
        return infoRepository.findByAbleServiceContaining(ableService, pageable);
    }

    public Page<PetSitter> findBySearchKeywordAndAbleServiceContaining(String searchKeyword, String ableService, Pageable pageable) {
        return infoRepository.findByWorkingAreaAndAbleServiceContaining(searchKeyword, ableService, pageable);
    }

    public void saveReview(Review review, PetSitter petsitter, String userEmail) {

        Member member = memberRepository.findByUserEmail(userEmail);

        review.setPetsitter(petsitter);
        review.setMember(member);

        reviewRepository.save(review);
    }

    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}

