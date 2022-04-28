package com.mandarin.petching.repository;

import com.mandarin.petching.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByUserEmail(String userEmail);

//    Member deleteById(Member member);
}