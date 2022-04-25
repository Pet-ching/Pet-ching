package com.mandarin.petching.repository;

import com.mandarin.petching.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByUserEmail(String userEmail);

//    Member deleteById(Member member);
}