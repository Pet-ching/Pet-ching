package com.mandarin.petching.repository;

import com.mandarin.petching.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<Member, Long> {
}




