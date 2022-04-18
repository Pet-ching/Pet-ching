// code by. hyeok
package com.mandarin.petching.repository;

import com.mandarin.petching.domain.PetSitter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InfoRepository extends JpaRepository<PetSitter, Long> {

    Page<PetSitter>  findByWorkingAreaContaining(String searchKeyword, Pageable pageable);
}
