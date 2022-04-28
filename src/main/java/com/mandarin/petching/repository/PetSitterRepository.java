package com.mandarin.petching.repository;

import com.mandarin.petching.domain.PetSitter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetSitterRepository extends JpaRepository<PetSitter, Long>  {
}
