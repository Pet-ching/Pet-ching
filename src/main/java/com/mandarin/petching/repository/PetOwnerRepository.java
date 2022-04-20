package com.mandarin.petching.repository;

import com.mandarin.petching.domain.Member;
import com.mandarin.petching.domain.PetOwner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetOwnerRepository extends JpaRepository<PetOwner, Long> {
}
